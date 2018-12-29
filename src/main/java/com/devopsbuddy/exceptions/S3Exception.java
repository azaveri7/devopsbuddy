package com.devopsbuddy.exceptions;

/**
 * Created by Zaveria on 29/12/2018.
 */
public class S3Exception extends RuntimeException {

    public S3Exception(Throwable e) {
        super(e);
    }

    public S3Exception(String s) {
        super(s);
    }
}