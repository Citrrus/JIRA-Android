package com.citrrus.jira;

import android.accounts.AccountsException;
import com.citrrus.jira.core.BootstrapService;
import com.citrrus.jira.authenticator.ApiKeyProvider;
import com.citrrus.jira.core.UserAgentProvider;
import android.app.Activity;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Provider for a {@link com.citrrus.jira.core.BootstrapService} instance
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
    public BootstrapService getService(Activity activity) throws IOException, AccountsException {
        return new BootstrapService(keyProvider.getAuthKey(activity), userAgentProvider);
    }
}
