package com.application;

import com.config.ConfigImpl;
import com.factory.ObjectFactory;

import java.util.Map;

public class Application {
    public static ApplicationContext run(String packageToScan, Map<Class, Class> ifcToImplClass) {
        ConfigImpl config = new ConfigImpl(packageToScan, ifcToImplClass);
        ApplicationContext context = new ApplicationContext(config);
        ObjectFactory objectFactory = new ObjectFactory(context);
        context.setFactory(objectFactory);
        return context;
    }
}
