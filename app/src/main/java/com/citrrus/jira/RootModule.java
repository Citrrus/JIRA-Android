package com.citrrus.jira;

import dagger.Module;

/**
 * Add all the other modules to this one.
 */
@Module
(
    library = true,
    includes = {
            AndroidModule.class,
            BootstrapModule.class
    }
)
public class RootModule {
}
