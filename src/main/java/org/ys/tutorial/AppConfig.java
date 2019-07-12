/*
 * Copyright (c) 2019. Yousif S
 */

package org.ys.tutorial;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;


import javax.inject.Singleton;

public class AppConfig extends ResourceConfig {

    public AppConfig(){

        // the pacakges to auto scan for REST resources
        packages("org.ys.tutorial");

        // dependency injection binding
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                 bind(SqliteTodoRepository.class).to(TodoRepository.class).in(Singleton.class);
            }
        });
    }
}