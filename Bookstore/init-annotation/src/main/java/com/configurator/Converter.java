package com.configurator;

import java.lang.reflect.Field;
import java.util.Arrays;

public class Converter {
    public static <T> void convert(Field field, T t, String value) throws Exception {
        Class type = field.getType();
        boolean isArray = false;
        String[] values;
        if (type.isArray()) {
            values = value.split(",");
            isArray = true;
            type = type.getComponentType();
        } else {
            values = new String[0];
        }
        int length = values.length;
        if (type.isAssignableFrom(Integer.class) || type.isAssignableFrom(int.class)) {
            if (isArray) {
                Integer[] list = new Integer[length];
                for (int i = 0; i < length; i++) {
                    list[i] = Integer.parseInt(values[i]);
                }
                if (type.isPrimitive()) {
                    field.set(t, Arrays.stream(list).mapToInt(i -> i).toArray());
                } else {
                    field.set(t, list);
                }
            } else {
                field.set(t, Integer.parseInt(value));
            }
        } else if (type.isAssignableFrom(Long.class) || type.isAssignableFrom(long.class)) {
            if (isArray) {
                Long[] list = new Long[length];
                for (int i = 0; i < length; i++) {
                    list[i] = Long.parseLong(values[i]);
                }
                if (type.isPrimitive()) {
                    field.set(t, Arrays.stream(list).mapToLong(i -> i).toArray());
                } else {
                    field.set(t, list);
                }
            } else {
                field.set(t, Long.parseLong(value));
            }
        } else if (type.isAssignableFrom(Double.class) || type.isAssignableFrom(double.class)) {
            if (isArray) {
                Double[] list = new Double[length];
                for (int i = 0; i < length; i++) {
                    list[i] = Double.parseDouble(values[i]);
                }
                if (type.isPrimitive()) {
                    field.set(t, Arrays.stream(list).mapToDouble(i -> i).toArray());
                } else {
                    field.set(t, list);
                }
            } else {
                field.set(t, Double.parseDouble(value));
            }
        } else if (type.isAssignableFrom(String.class)) {
            if (isArray) {
                String[] list = new String[length];
                for (int i = 0; i < length; i++) {
                    list[i] = values[i];
                }
                field.set(t, list);
            } else {
                field.set(t, value);
            }
        } else if (type.isAssignableFrom(Boolean.class) || type.isAssignableFrom(boolean.class)) {
            if (isArray) {
                if (type.isPrimitive()) {
//                    boolean [] result = Stream.of(list).map(Boolean::booleanValue).toArray(boolean[]::new);
                    boolean[] list = new boolean[length];
                    for (int i = 0; i < length; i++) {
                        list[i] = Boolean.parseBoolean(values[i]);
                    }
                    field.set(t, list);
                } else {
                    Boolean[] list = new Boolean[length];
                    for (int i = 0; i < length; i++) {
                        list[i] = Boolean.parseBoolean(values[i]);
                    }
                    field.set(t, list);
                }
            } else {
                field.set(t, Boolean.parseBoolean(value));
            }
        } else {
            throw new Exception("Неподдерживаемый тип!");
        }
    }
}
