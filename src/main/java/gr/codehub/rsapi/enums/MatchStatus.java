package gr.codehub.rsapi.enums;

public enum MatchStatus {
    FINALISED(0),
    UNFINALISED(1),
    DELETED(2);

    private final int value;

    MatchStatus(int value) {
        this.value = value;
    }
}
