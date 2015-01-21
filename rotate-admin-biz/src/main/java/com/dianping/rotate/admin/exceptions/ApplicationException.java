package com.dianping.rotate.admin.exceptions;

/**
 * Created by shenyoujun on 15/1/20.
 */
public class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
