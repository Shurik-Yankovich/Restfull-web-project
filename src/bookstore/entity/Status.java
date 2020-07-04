package bookstore.entity;

import bookstore.exeption.GetStatusException;

public enum Status {
    NEW, COMPLETED, CANCELED;

    public static Status getStatus(String statusText) throws GetStatusException {
        switch (statusText) {
            case "NEW":
                return NEW;
            case "COMPLETED":
                return COMPLETED;
            case "CANCELED":
                return CANCELED;
            default:
                throw new GetStatusException("Wrong status!");
        }
    }
}
