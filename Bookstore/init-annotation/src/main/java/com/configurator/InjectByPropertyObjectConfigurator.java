package com.configurator;

import com.application.ApplicationContext;
import com.annotation.InjectByProperty;
import com.factory.ObjectFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

public class InjectByPropertyObjectConfigurator implements ObjectConfigurator {
//    private static final Logger logger = LogManager.getLogger(InjectByPropertyObjectConfigurator.class);

    /*public InjectByPropertyObjectConfigurator() {
        logger = new LoggerApp(InjectByPropertyObjectConfigurator.class);
    }*/

    @Override
    public void configure(Object t, ApplicationContext context) throws Exception{
        Class<? extends Object> implClass = t.getClass();
        for (Field field : implClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectByProperty.class)) {
                InjectByProperty annotation = field.getAnnotation(InjectByProperty.class);
                if (annotation != null) {
                    String configFileName = annotation.configName().isEmpty() ? "config.properties" : annotation.configName();
                    try {
                        String path = ClassLoader.getSystemClassLoader().getResource(configFileName).getPath();
                        Properties properties = new Properties();
                        properties.load(new FileInputStream(path));
                        String value = annotation.propertyName().isEmpty() ? properties.getProperty(implClass.getName() + "." + field.getName()) : properties.getProperty(annotation.propertyName());
                        field.setAccessible(true);
                        switch (annotation.type()) {
                            case INTEGER:
                                field.set(t, Integer.parseInt(value));
                                break;
                            case BOOLEAN:
                                field.set(t, Boolean.parseBoolean(value));
                                break;
                            case STRING:
                                field.set(t, value);
                                break;
                            case DEFAULT:
                                Converter.convert(field,t,value);
                        }
                    } catch (IOException e) {
                        System.err.println("ОШИБКА: Файл отсуствует!\n" + e.getMessage());
//                        logger.error("ОШИБКА: Файл отсуствует!\n" + e.getMessage());
                    } catch (NullPointerException e) {
                        System.err.println("ОШИБКА: Не найден путь к ресурсам!\n" + e.getMessage());
//                        logger.error("ОШИБКА: Не найден путь к ресурсам!\n" + e.getMessage());
                    } catch (IllegalAccessException e) {
                        System.err.println("ОШИБКА: Неудалось установить значение поля!\n" + e.getMessage());
//                        logger.error("ОШИБКА: Неудалось установить значение поля!\n" + e.getMessage());
                    } catch (Exception e) {
                        System.err.println("ОШИБКА!\n" + e.getMessage());
//                        logger.error("ОШИБКА!\n" + e.getMessage());
                    }
                }
            }
        }
    }
}
