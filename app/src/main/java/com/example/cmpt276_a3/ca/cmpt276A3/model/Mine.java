package com.example.cmpt276_a3.ca.cmpt276A3.model;

public class Mine {
    private boolean revealed = false;
    private boolean isPresent = false;

    public Mine(boolean revealed, boolean isPresent) {
        this.revealed = revealed;
        this.isPresent = isPresent;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void reveal() {
        this.revealed = true;
    }

    public boolean isPresent() {
        return isPresent;
    }
}
