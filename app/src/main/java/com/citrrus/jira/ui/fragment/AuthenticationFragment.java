package com.citrrus.jira.ui.fragment;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.InjectView;
import com.citrrus.jira.R;
import com.citrrus.jira.event.FlipToSigninEvent;
import com.citrrus.jira.event.SignInEvent;
import com.citrrus.jira.ui.view.TextWatcherAdapter;
import com.squareup.otto.Bus;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.KEYCODE_ENTER;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;

/**
 * Created with IntelliJ IDEA.
 * User: jonwillis
 * Date: 2/26/13
 * Time: 12:50 AM
 */
public abstract class AuthenticationFragment extends BootstrapFragment {

    @Inject protected Bus BUS;

    protected TextWatcher watcher = validationTextWatcher();

    protected AccountManager accountManager;

    @InjectView(R.id.et_email)
    protected AutoCompleteTextView etEmail;

    @InjectView(R.id.et_password)
    protected EditText etPassword;

    @InjectView(R.id.b_signin)
    protected Button bSignin;

    @InjectView(R.id.tv_signup)
    protected TextView tvSignup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountManager = AccountManager.get(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etEmail.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, userEmailAccounts()));

        etPassword.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event != null && ACTION_DOWN == event.getAction()
                        && keyCode == KEYCODE_ENTER && bSignin.isEnabled()) {
                    BUS.post(new SignInEvent(etEmail.getText().toString(), etPassword.getText().toString(), etPassword.getText().toString()));
                    return true;
                }
                return false;
            }
        });

        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == IME_ACTION_DONE && bSignin.isEnabled()) {
                    BUS.post(new SignInEvent(etEmail.getText().toString(), etPassword.getText().toString(), etPassword.getText().toString()));
                    return true;
                }
                return false;
            }
        });

        etEmail.addTextChangedListener(watcher);
        etPassword.addTextChangedListener(watcher);

        tvSignup.setText(Html.fromHtml(getString(R.string.signup_link)));
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BUS.post(new FlipToSigninEvent());
            }
        });
    }

    private List<String> userEmailAccounts() {
        Account[] accounts = accountManager.getAccountsByType("com.google");
        List<String> emailAddresses = new ArrayList<String>(accounts.length);
        for (Account account : accounts) {
            emailAddresses.add(account.name);
        }
        return emailAddresses;
    }

    private TextWatcher validationTextWatcher() {
        return new TextWatcherAdapter() {
            public void afterTextChanged(Editable gitDirEditText) {
                updateUIWithValidation();
            }

        };
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUIWithValidation();
    }

    protected void updateUIWithValidation() {
        boolean populated = populated(etEmail) && populated(etPassword);
        bSignin.setEnabled(populated);
    }

    protected boolean populated(EditText editText) {
        return editText.length() > 0;
    }
}
