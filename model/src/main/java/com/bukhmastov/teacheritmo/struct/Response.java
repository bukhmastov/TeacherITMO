package com.bukhmastov.teacheritmo.struct;

public class Response<T> {

    private Status status;
    private Throwable throwable;
    private T data;

    private Response(Status status) {
        this.status = status;
    }

    private Response(Status status, T data) {
        this.status = status;
        this.data = data;
    }

    private Response(Status status, Throwable throwable) {
        this.status = status;
        this.throwable = throwable;
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

    public static <T> Response<T> error(Throwable throwable) {
        return new Response<>(Status.ERROR, throwable);
    }

    public static <T> Response<T> error(Response<?> response) {
        return new Response<>(Status.ERROR, response.getException());
    }

    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", data=" + data +
                ", throwable=" + throwable +
                '}';
    }
}
