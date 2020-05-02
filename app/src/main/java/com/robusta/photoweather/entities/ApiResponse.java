package com.robusta.photoweather.entities;

public class ApiResponse<T> {

    private int  status;
    private boolean error;
    private T body;

    public ApiResponse(int status, boolean error, T body) {
        this.status = status;
        this.error = error;
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
