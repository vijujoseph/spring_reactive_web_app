package com.reactive.web.reactivewebapp.dto;

public class ResponseDto<T>  {
    private Boolean success;
    private T data;
    private String error;

    public ResponseDto(Boolean success, T data, String error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<T>(true, data, null);
    }

    @SuppressWarnings("unchecked")
    public static <T, S> ResponseDto<T> fail(String error, S clazz) {
        return (ResponseDto<T>) new ResponseDto<S>(false, null, error);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
