
package com.donnfelker.android.bootstrap;

import android.accounts.AccountsException;

import com.donnfelker.android.bootstrap.authenticator.ApiKeyProvider;
import com.donnfelker.android.bootstrap.core.BootstrapService;
import com.donnfelker.android.bootstrap.core.UserAgentProvider;
import com.google.inject.Inject;

import java.io.IOException;

/**
 * Provider for a {@link com.donnfelker.android.bootstrap.core.BootstrapService} instance
 */
public class BootstrapServiceProvider {

    @Inject private ApiKeyProvider keyProvider;
    @Inject private UserAgentProvider userAgentProvider;

    /**
     * Get service for configured key provider
     * <p>
     * This method gets an auth key and so it blocks and shouldn't be called on the main thread.
     *
     * @return bootstrap service
     * @throws IOException
     * @throws AccountsException
     */
    public BootstrapService getService() throws IOException, AccountsException {
        return new BootstrapService(keyProvider.getAuthKey(), userAgentProvider);
    }
}
