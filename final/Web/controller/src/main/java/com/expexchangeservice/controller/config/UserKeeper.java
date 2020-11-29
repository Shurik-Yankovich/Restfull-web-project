package com.expexchangeservice.controller.config;

import com.expexchangeservice.model.entities.User;

public class UserKeeper {

    private static final ThreadLocal<User> threadLocalScope = new  ThreadLocal<>();

    public final static User getLoggedUser() {
        return threadLocalScope.get();
    }

    public final static void setLoggedUser(User user) {
        threadLocalScope.set(user);
    }

    public final static void deleteLoggedUser() {
        threadLocalScope.remove();
    }
}
