package org.velz.tennismatch.enums;

import lombok.Getter;

@Getter
public enum EPlayer {
    PLAYER1(0),
    PLAYER2(1);

    private final int indexPlayer;

    EPlayer(int indexPlayer) {
        this.indexPlayer = indexPlayer;
    }

}
