package com.mapper;

import com.annotation.ConfigProperty;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

public class Mapper {

    public static <T> void getValue(T t) {
//        Class<? extends T> implClass = type;
        Class<? extends Object> implClass = t.getClass();
        for (Field field : implClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(ConfigProperty.class)) {
                ConfigProperty annotation = field.getAnnotation(ConfigProperty.class);
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
                                field.set(t, field.getType().cast(value));
                        }
                    } catch (IOException e) {
                        System.err.println("ОШИБКА: Файл отсуствует!\n" + e.getMessage());
                    } catch (NullPointerException e) {
                        System.err.println("ОШИБКА: Не найден путь к ресурсам!\n" + e.getMessage());
                    } catch (IllegalAccessException e) {
                        System.err.println("ОШИБКА: Неудалось установить значение поля!\n" + e.getMessage());
                    }
                }
            }
        }

    }

//    private static <T> void converter(Field field, T t, String value) throws IllegalAccessException {
//        Class type = field.getType();
//        if (type.isArray()) {
//        } else if (type.isPrimitive()) {
//
//        } else if (type.isInterface()) {
//
//        } else {
//            field.set(t, field.getType().cast(value));
//        }
//    }
}
