package gr.codehub.rsapi.enums;

public enum DegreeLevel {
    HIGH_SCHOOL_DIPLOMA(0),
    ASSOCIATE(1),
    BACHELOR(2),
    MASTER(3),
    PHD(4)
    ;




    private int value;

    DegreeLevel(int value) {
        this.value = value;
    }
}
