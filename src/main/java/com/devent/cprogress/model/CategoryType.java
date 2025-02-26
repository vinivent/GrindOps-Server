package com.devent.cprogress.model;

public enum CategoryType {
    CAMPANHA("campanha"),
    ZOMBIES("zombies"),
    MULTIPLAYER("multiplayer"),
    WARZONE("warzone");

    private String category;

    CategoryType(String category) {
        this.category = category;
    }
    public String getRole() {
        return category;
    }
}
