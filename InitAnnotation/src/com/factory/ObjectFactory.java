package com.factory;

import com.application.ApplicationContext;
import com.configurator.ObjectConfigurator;

import java.util.ArrayList;
import java.util.List;

public class ObjectFactory {
    private final ApplicationContext context;
    private List<ObjectConfigurator> configurators = new ArrayList<>();

    public ObjectFactory(ApplicationContext context) {
        this.context = context;
        try {
            for (Class<? extends ObjectConfigurator> aClass : context.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class)) {
                configurators.add(aClass.getDeclaredConstructor().newInstance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> T createObject(Class<T> implClass) {
        T t = null;
        try {
            t = create(implClass);
            configure(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
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
