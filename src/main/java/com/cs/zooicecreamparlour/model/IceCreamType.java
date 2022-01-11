package com.cs.zooicecreamparlour.model;

public enum IceCreamType {
    ROCKY_ROAD("Rocky Road"),
    COOKIES_AND_CREAM("Cookies & Cream"),
    NETFLIX_AND_CHILL("Netflix & Chill");

    private final String description;

    IceCreamType(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
