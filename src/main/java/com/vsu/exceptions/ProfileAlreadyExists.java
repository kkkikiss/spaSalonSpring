package com.vsu.exceptions;

public class ProfileAlreadyExists extends RuntimeException{
    public ProfileAlreadyExists(final String msg) {
        super(msg);
    }
}
