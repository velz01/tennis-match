package org.velz.tennismatch.enums;

import lombok.Getter;

@Getter
public enum Points {
    ZERO(0),
    FIFTEEN(15),
    THIRTY(30),
    FORTY(40);
    private final int points;

    Points(int points) {
        this.points = points;
    }

    public Points next() {
        return switch (this) {
            case ZERO -> FIFTEEN;
            case FIFTEEN -> THIRTY;
            case THIRTY, FORTY -> FORTY;
        };
    }

}
