package com.jondwillis.vapordex;

import android.accounts.AccountsException;
import com.google.inject.Inject;
import com.jondwillis.vapordex.authenticator.ApiKeyProvider;
import com.jondwillis.vapordex.core.BootstrapService;
import com.jondwillis.vapordex.core.UserAgentProvider;

import java.io.IOException;

/**
 * Provider for a {@link com.jondwillis.vapordex.core.BootstrapService} instance
 */
public class BootstrapServiceProvider {

    @Inject
    private ApiKeyProvider keyProvider;
    @Inject
    private UserAgentProvider userAgentProvider;

    /**
     * Get service for configured key provider
     * <p/>
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
