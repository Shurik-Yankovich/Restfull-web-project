package com.factory;

import com.application.ApplicationContext;
import com.config.ConfigImpl;
import com.configurator.ObjectConfigurator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ObjectFactory {
    private final ApplicationContext context;
    private List<ObjectConfigurator> configurators = new ArrayList<>();

    public ObjectFactory(ApplicationContext context) {
        this.context = context;
        ConfigImpl config = new ConfigImpl("com", new HashMap<>());
        try {
            for (Class<? extends ObjectConfigurator> aClass : config.getScanner().getSubTypesOf(ObjectConfigurator.class)) {
                configurators.add(aClass.getDeclaredConstructor().newInstance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> T createObject(Class<T> implClass) {
        try {
            T t = create(implClass);
            configure(t);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> void configure(T t) {
        configurators.forEach(objectConfigurator -> {
            try {
                objectConfigurator.configure(t, context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private <T> T create(Class<T> implClass) throws InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException, NoSuchMethodException {
        return implClass.getDeclaredConstructor().newInstance();
    }
}
