package org.velz.tennismatch.enums;

public enum EPlayer {
    PLAYER1(0),
    PLAYER2(1);

    private int indexPlayer;

    EPlayer(int indexPlayer) {
        this.indexPlayer = indexPlayer;
    }

    public int getIndexPlayer() {
        return indexPlayer;
    }

}
