package gr.codehub.rsapi.enums;

public enum DegreeLevel {
    HIGH_SCHOOL_DIPLOMA(0, "High School Diploma"),
    ASSOCIATE(1, "Associate"),
    BACHELOR(2, "Bachelor"),
    MASTER(3, "Master"),
    PHD(4, "Phd");


    private int value;
    private String  level;

    DegreeLevel(int value, String level) {
        this.value = value;
        this.level = level;
    }

    public static DegreeLevel findDegreeLevel(String level) {
        for (DegreeLevel degreeLevel : DegreeLevel.values()) {
            if (degreeLevel.level.equals(level))
                return degreeLevel;
        }
        return DegreeLevel.findDegreeLevel(level);
    }
}
