package bookstore.entity;

import bookstore.exeption.StatusException;

import java.io.Serializable;

public enum Status implements Serializable {
    NEW, COMPLETED, CANCELED;

    public static Status getStatus(String statusText) throws StatusException {
        switch (statusText) {
            case "NEW":
                return NEW;
            case "COMPLETED":
                return COMPLETED;
            case "CANCELED":
                return CANCELED;
            default:
                throw new StatusException("Wrong status!");
        }
    }
}
