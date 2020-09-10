package com.factory;

import com.annotation.PostConstruct;
import com.application.ApplicationContext;
import com.config.ConfigImpl;
import com.configurator.ObjectConfigurator;
import logger.LoggerApp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ObjectFactory {

    private final ApplicationContext context;
    private List<ObjectConfigurator> configurators = new ArrayList<>();
    private final LoggerApp logger = new LoggerApp(this.getClass());

    public ObjectFactory(ApplicationContext context) {
        this.context = context;
        ConfigImpl config = new ConfigImpl("com", new HashMap<>());
        try {
            for (Class<? extends ObjectConfigurator> aClass : config.getScanner().getSubTypesOf(ObjectConfigurator.class)) {
                configurators.add(aClass.getDeclaredConstructor().newInstance());
            }
        } catch (Exception e) {
            logger.errorLogger(e.getMessage());
        }
    }

    public <T> T createObject(Class<T> implClass) {
        try {
            T t = create(implClass);
            configure(t);
            invokeInitialization(implClass, t);
            return t;
        } catch (Exception e) {
//            e.printStackTrace();
            logger.errorLogger("Неудалось создать объект\n" + e.getMessage());
        }
        return null;
    }

    private <T> T create(Class<T> implClass) throws InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException, NoSuchMethodException {
        return implClass.getDeclaredConstructor().newInstance();
    }


    private <T> void configure(T t) {
        configurators.forEach(objectConfigurator -> {
            try {
                objectConfigurator.configure(t, context);
            } catch (Exception e) {
                logger.errorLogger("Неудалось настроить объект\n" + e.getMessage());
            }
        });
    }

    private <T> void invokeInitialization(Class<T> implClass, T t) throws IllegalAccessException, InvocationTargetException {
        for (Method method : implClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.invoke(t);
            }
        }
    }
}
