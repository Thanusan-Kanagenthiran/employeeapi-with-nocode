package com.tmk.inventorymanager.employeeapi.employeeapi.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
