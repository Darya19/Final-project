package com.epam.enrollee.controller.router;

public class Router {

    public enum Type {
        FORWARD,
        REDIRECT;
    }

    private Type type;
    private String page;


    public Router(String page) {
        this.type = Type.FORWARD;
        this.page = page;
    }

    public Router(Type type, String page) {
        this.type = type;
        this.page = page;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
