package com.devent.cprogress.model;

public enum ChallengeType {
    CARREIRA("carreira"),
    PRESTIGIO("prestigio"),
    DARKOPS("darkops"),
    CAMUFLAGEM("camuflagem");

    private String challenge;

    ChallengeType(String challenge) {
        this.challenge = challenge;
    }

    public String getChallenge() {
        return challenge;
    }
}
