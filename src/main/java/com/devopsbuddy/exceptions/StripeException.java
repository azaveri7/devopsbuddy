package com.devopsbuddy.exceptions;

/**
 * Created by Zaveria on 29/12/2018.
 */
public class StripeException extends RuntimeException {

    public StripeException(Throwable e) {
        super(e);
    }

}