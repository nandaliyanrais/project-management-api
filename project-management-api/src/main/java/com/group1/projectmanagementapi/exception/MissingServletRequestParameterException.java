package com.group1.projectmanagementapi.exception;

public class MissingServletRequestParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MissingServletRequestParameterException(String msg) {
        super(msg);
    }

}
