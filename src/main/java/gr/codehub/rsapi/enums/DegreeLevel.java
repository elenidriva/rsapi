package gr.codehub.rsapi.enums;

public enum DegreeLevel {
    COMPUTER_GRAPHICS(0, "Computer Graphics"),
    COMPUTER_SCIENCE(1, "Computer Science"),
    BACHELOR(2, "Bachelor"),
    MASTER(3, "Master"),
    PHD(4, "Phd");


    private final int value;
    private final String level;

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
