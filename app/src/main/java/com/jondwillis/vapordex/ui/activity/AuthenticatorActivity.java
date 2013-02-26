package com.jondwillis.vapordex.ui.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.wishlist.Toaster;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.jondwillis.vapordex.R;
import com.jondwillis.vapordex.core.Constants;
import com.jondwillis.vapordex.core.User;
import com.jondwillis.vapordex.event.FlipToSigninEvent;
import com.jondwillis.vapordex.event.SignInEvent;
import com.jondwillis.vapordex.ui.fragment.LoginFragment;
import com.jondwillis.vapordex.ui.fragment.SignupFragment;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import roboguice.util.Ln;
import roboguice.util.RoboAsyncTask;
import roboguice.util.Strings;

import static android.accounts.AccountManager.KEY_ACCOUNT_NAME;
import static android.accounts.AccountManager.KEY_ACCOUNT_TYPE;
import static android.accounts.AccountManager.KEY_AUTHTOKEN;
import static android.accounts.AccountManager.KEY_BOOLEAN_RESULT;
import static com.github.kevinsawicki.http.HttpRequest.get;
import static com.jondwillis.vapordex.core.Constants.Http.HEADER_PARSE_APP_ID;
import static com.jondwillis.vapordex.core.Constants.Http.HEADER_PARSE_REST_API_KEY;
import static com.jondwillis.vapordex.core.Constants.Http.PARSE_APP_ID;
import static com.jondwillis.vapordex.core.Constants.Http.PARSE_REST_API_KEY;
import static com.jondwillis.vapordex.core.Constants.Http.URL_AUTH;

/**
 * Activity to authenticate the user against an API (example API on Parse.com)
 */
public class AuthenticatorActivity extends
        RoboSherlockAccountAuthenticatorFragmentActivity
        implements FragmentManager.OnBackStackChangedListener {

    /**
     * PARAM_CONFIRMCREDENTIALS
     */
    public static final String PARAM_CONFIRMCREDENTIALS = "confirmCredentials";

    /**
     * PARAM_PASSWORD
     */
    public static final String PARAM_PASSWORD = "password";

    /**
     * PARAM_USERNAME
     */
    public static final String PARAM_USERNAME = "username";

    /**
     * PARAM_AUTHTOKEN_TYPE
     */
    public static final String PARAM_AUTHTOKEN_TYPE = "authtokenType";


    private AccountManager accountManager;

    @Inject
    private Bus BUS;

    private RoboAsyncTask<Boolean> authenticationTask;
    private String authToken;
    private String authTokenType;

    /**
     * If set we are just checking that the user knows their credentials; this
     * doesn't cause the user's password to be changed on the device.
     */
    private Boolean confirmCredentials = false;

    private String email;

    private String password;


    /**
     * In this instance the token is simply the sessionId returned from Parse.com. This could be a
     * oauth token or some other type of timed token that expires/etc. We're just using the parse.com
     * sessionId to prove the example of how to utilize a token.
     */
    private String token;

    /**
     * Was the original caller asking for an entirely new account?
     */
    protected boolean requestNewAccount = false;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountManager = AccountManager.get(this);
        final Intent intent = getIntent();
        email = intent.getStringExtra(PARAM_USERNAME);
        authTokenType = intent.getStringExtra(PARAM_AUTHTOKEN_TYPE);
        requestNewAccount = email == null;
        confirmCredentials = intent.getBooleanExtra(PARAM_CONFIRMCREDENTIALS,
                false);

        setContentView(R.layout.view_fragment);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, new LoginFragment())
                    .commit();
        } else {
            mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
        }

        // Monitor back stack changes to ensure the action bar shows the appropriate
        // button (either "photo" or "info").
        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BUS.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BUS.unregister(this);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getText(R.string.message_signing_in));
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                if (authenticationTask != null) {
                    authenticationTask.cancel(true);
                }
            }
        });
        return dialog;
    }

    @Subscribe
    public void onSignInEvent(SignInEvent event) {
        if (authenticationTask != null) {
            return;
        }

        if (requestNewAccount) {
            email = event.getEmail().toString();
        }
        password = event.getPassword().toString();
        showProgress();

        authenticationTask = new RoboAsyncTask<Boolean>(this) {
            public Boolean call() throws Exception {

                final String query = String.format("%s=%s&%s=%s", PARAM_USERNAME, email, PARAM_PASSWORD, password);

                HttpRequest request = get(URL_AUTH + "?" + query)
                        .header(HEADER_PARSE_APP_ID, PARSE_APP_ID)
                        .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY);


                Log.d("Auth", "response=" + request.code());

                if (request.ok()) {
                    final User model = new Gson().fromJson(Strings.toString(request.buffer()), User.class);
                    token = model.getSessionToken();
                }

                return request.ok();
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                Throwable cause = e.getCause() != null ? e.getCause() : e;

                String message;
                // A 404 is returned as an Exception with this message
                if ("Received authentication challenge is null".equals(cause
                        .getMessage())) {
                    message = getResources().getString(
                            R.string.message_bad_credentials);
                } else {
                    message = cause.getMessage();
                }

                Toaster.showLong(AuthenticatorActivity.this, message);
            }

            @Override
            public void onSuccess(Boolean authSuccess) {
                onAuthenticationResult(authSuccess);
            }

            @Override
            protected void onFinally() throws RuntimeException {
                hideProgress();
                authenticationTask = null;
            }
        };
        authenticationTask.execute();
    }

    /**
     * Called when response is received from the server for confirm credentials
     * request. See onAuthenticationResult(). Sets the
     * AccountAuthenticatorResult which is sent back to the caller.
     *
     * @param result
     */
    protected void finishConfirmCredentials(boolean result) {
        final Account account = new Account(email, Constants.Auth.BOOTSTRAP_ACCOUNT_TYPE);
        accountManager.setPassword(account, password);

        final Intent intent = new Intent();
        intent.putExtra(KEY_BOOLEAN_RESULT, result);
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Called when response is received from the server for authentication
     * request. See onAuthenticationResult(). Sets the
     * AccountAuthenticatorResult which is sent back to the caller. Also sets
     * the authToken in AccountManager for this account.
     */

    protected void finishLogin() {
        final Account account = new Account(email, Constants.Auth.BOOTSTRAP_ACCOUNT_TYPE);

        if (requestNewAccount) {
            accountManager.addAccountExplicitly(account, password, null);
        } else {
            accountManager.setPassword(account, password);
        }
        final Intent intent = new Intent();
        authToken = token;
        intent.putExtra(KEY_ACCOUNT_NAME, email);
        intent.putExtra(KEY_ACCOUNT_TYPE, Constants.Auth.BOOTSTRAP_ACCOUNT_TYPE);
        if (authTokenType != null
                && authTokenType.equals(Constants.Auth.AUTHTOKEN_TYPE)) {
            intent.putExtra(KEY_AUTHTOKEN, authToken);
        }
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Hide progress dialog
     */
    @SuppressWarnings("deprecation")
    protected void hideProgress() {
        dismissDialog(0);
    }

    /**
     * Show progress dialog
     */
    @SuppressWarnings("deprecation")
    protected void showProgress() {
        showDialog(0);
    }

    /**
     * Called when the authentication process completes (see attemptLogin()).
     *
     * @param result
     */
    public void onAuthenticationResult(boolean result) {
        if (result) {
            if (!confirmCredentials) {
                finishLogin();
            } else {
                finishConfirmCredentials(true);
            }
        } else {
            Ln.d("onAuthenticationResult: failed to authenticate");
            if (requestNewAccount) {
                Toaster.showLong(AuthenticatorActivity.this,
                        R.string.message_auth_failed_new_account);
            } else {
                Toaster.showLong(AuthenticatorActivity.this,
                        R.string.message_auth_failed);
            }
        }
    }


    private boolean mShowingBack = false;

    @Subscribe
    public void onFlipToSigninEvent(FlipToSigninEvent event) {
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            return;
        }

        // Flip to the back.

        mShowingBack = true;

        getSupportActionBar().setDisplayHomeAsUpEnabled(mShowingBack);

        // Create and commit a new fragment transaction that adds the fragment for the back of
        // the card, uses custom animations, and is part of the fragment manager's back stack.

        getSupportFragmentManager()
                .beginTransaction()

                        // Replace the default fragment animations with animator resources representing
                        // rotations when switching to the back of the card, as well as animator
                        // resources representing rotations when flipping back to the front (e.g. when
                        // the system Back button is pressed).
                .setCustomAnimations(
                        R.anim.card_flip_right_in, R.anim.card_flip_right_out,
                        R.anim.card_flip_left_in, R.anim.card_flip_left_out)

                        // Replace any fragments currently in the container view with a fragment
                        // representing the next page (indicated by the just-incremented currentPage
                        // variable).
                .replace(R.id.fragment_container, new SignupFragment())

                        // Add this transaction to the back stack, allowing users to press Back
                        // to get to the front of the card.
                .addToBackStack(null)

                        // Commit the transaction.
                .commit();
    }

    @Override
    public void onBackStackChanged() {
        mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(mShowingBack);

        // When the back stack changes, invalidate the options menu (action bar).
        invalidateOptionsMenu();
    }
}
