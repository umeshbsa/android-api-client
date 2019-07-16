package com.app.apiclient.model.base;

public class BaseResponse<T> {

    public int statusCode;
    public T responseData;
    public Errors error;
}
