package com.config;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

public class ConfigImpl implements Config {

    private Reflections scanner;
    private Map<Class, Class> ifcToImplClass;

    public ConfigImpl(String packageToScan, Map<Class, Class> ifcToImplClass) {
        this.ifcToImplClass = ifcToImplClass;
        this.scanner = new Reflections(packageToScan);
    }

    @Override
    public Reflections getScanner() {
        return scanner;
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {
        return ifcToImplClass.computeIfAbsent(ifc, aClass -> {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc);
            if (classes.size() != 1) {
                throw new RuntimeException(ifc + " has 0 or more than one impl please update your config");
            }

            return classes.iterator().next();
        });
    }
}
