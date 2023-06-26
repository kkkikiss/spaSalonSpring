package com.vsu.exceptions;

public class NotFound extends RuntimeException{
    public NotFound(final String msg) {
        super(msg);
    }
}
