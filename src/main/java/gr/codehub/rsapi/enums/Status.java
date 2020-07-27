package gr.codehub.rsapi.enums;

public enum Status {
    ACTIVE(0),
    INACTIVE(1);

    private int value;

    Status(int value) {
        this.value = value;
    }
}
