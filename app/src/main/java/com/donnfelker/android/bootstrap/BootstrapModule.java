package com.donnfelker.android.bootstrap;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module for setting up provides statements.
 */
@Module
(
        complete = false,

        entryPoints = {
            BootstrapApplication.class
        },

        includes = {
                AndroidModule.class
        }
)
public class BootstrapModule  {

    @Singleton
    @Provides
    Bus provideOttoBus() {

        return new Bus();

    }

}
