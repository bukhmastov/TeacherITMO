package com.bukhmastov.teacheritmo.struct;

public class Response<T> {

    private Status status;
    private Throwable throwable;
    private T data;

    public Response(Status status) {
        this.status = status;
    }

    public Response(Status status, T data) {
        this.status = status;
        this.data = data;
    }

    public Response(Status status, Throwable throwable) {
        this.status = status;
        this.throwable = throwable;
    }

    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public Throwable getException() {
        return throwable;
    }

    public boolean isOk() {
        return status == Status.OK;
    }

    public boolean isError() {
        return !isOk();
    }

    public boolean is(Status status) {
        return this.status == status;
    }

    public static <T> Response<T> ok() {
        return new Response<>(Status.OK);
    }

    public static <T> Response<T> ok(T data) {
        return new Response<>(Status.OK, data);
    }

    public static <T> Response<T> error(Status status) {
        return new Response<>(status);
    }

    public static <T> Response<T> error(Throwable throwable) {
        return new Response<>(Status.ERROR, throwable);
    }

    public static <T> Response<T> error(Status status, Throwable throwable) {
        return new Response<>(status, throwable);
    }
}
