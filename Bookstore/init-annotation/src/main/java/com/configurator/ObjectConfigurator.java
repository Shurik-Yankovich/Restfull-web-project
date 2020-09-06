package com.configurator;

import com.application.ApplicationContext;

public interface ObjectConfigurator {
    void configure(Object object, ApplicationContext context) throws Exception;
}
