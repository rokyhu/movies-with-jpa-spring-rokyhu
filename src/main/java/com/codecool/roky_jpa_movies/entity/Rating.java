package com.codecool.roky_jpa_movies.entity;

public enum Rating {

    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    public int symbol;

    Rating(int symbol) {
        this.symbol = symbol;
    }
}
