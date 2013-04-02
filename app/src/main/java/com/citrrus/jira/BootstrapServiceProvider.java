package com.citrrus.jira;

import android.accounts.AccountsException;
import com.citrrus.jira.core.ApiService;
import com.citrrus.jira.authenticator.ApiKeyProvider;
import com.citrrus.jira.core.UserAgentProvider;
import android.app.Activity;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Provider for a {@link com.citrrus.jira.core.ApiService} instance
 */
public class BootstrapServiceProvider {

    @Inject ApiKeyProvider keyProvider;
    @Inject UserAgentProvider userAgentProvider;

    /**
     * Get service for configured key provider
     * <p/>
     * This method gets an auth key and so it blocks and shouldn't be called on the main thread.
     *
     * @return bootstrap service
     * @throws IOException
     * @throws AccountsException
     */
    public ApiService getService(Activity activity) throws IOException, AccountsException {
        return new ApiService(keyProvider.getAuthKey(activity), userAgentProvider);
    }
}
