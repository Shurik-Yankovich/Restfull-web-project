package com.handler;

import com.Converter;
import com.annotation.ConfigProperty;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

public class PropertiesAnnotationHandler {

    public static <T> void setValues(T t) {
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
                                Converter.converter(field,t,value);
                        }
                    } catch (IOException e) {
                        System.err.println("ОШИБКА: Файл отсуствует!\n" + e.getMessage());
                    } catch (NullPointerException e) {
                        System.err.println("ОШИБКА: Не найден путь к ресурсам!\n" + e.getMessage());
                    } catch (IllegalAccessException e) {
                        System.err.println("ОШИБКА: Неудалось установить значение поля!\n" + e.getMessage());
                    } catch (Exception e) {
                        System.err.println("ОШИБКА!\n" + e.getMessage());
                    }
                }
            }
        }

    }

//    private static <T> void converter(Field field, T t, String value) throws Exception {
//        Class type = field.getType();
//        if (type.isAssignableFrom(Integer.class) || type.isAssignableFrom(int.class)) {
//            field.set(t, Integer.parseInt(value));
//        } else if (type.isAssignableFrom(Long.class) || type.isAssignableFrom(long.class)) {
//            field.set(t, Long.parseLong(value));
//        } else if (type.isAssignableFrom(Short.class) || type.isAssignableFrom(short.class)) {
//            field.set(t, Short.parseShort(value));
//        } else if (type.isAssignableFrom(Byte.class) || type.isAssignableFrom(byte.class)) {
//            field.set(t, Byte.parseByte(value));
//        } else if (type.isAssignableFrom(Float.class) || type.isAssignableFrom(float.class)) {
//            field.set(t, Float.parseFloat(value));
//        } else if (type.isAssignableFrom(Double.class) || type.isAssignableFrom(double.class)) {
//            field.set(t, Double.parseDouble(value));
//        } else if (type.isAssignableFrom(Character.class) || type.isAssignableFrom(char.class)) {
//            field.set(t, value.charAt(0));
//        } else if (type.isAssignableFrom(String.class)) {
//            field.set(t, value);
//        } else if (type.isAssignableFrom(Boolean.class) || type.isAssignableFrom(boolean.class)) {
//            field.set(t, Boolean.parseBoolean(value));
//        } else {
//            throw new Exception("Неподдерживаемый тип!");
//        }
//    }
}
