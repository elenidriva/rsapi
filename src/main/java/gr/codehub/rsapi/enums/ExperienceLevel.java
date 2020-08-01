package gr.codehub.rsapi.enums;

public enum ExperienceLevel {
    ENTRY(0, "Entry"),
    JUNIOR(1, "Junior"),
    MID(2, "Mid"),
    SENIOR(3, "Senior");


    private final int value;
    private final String expLevel;

    ExperienceLevel(int value, String expLevel) {
        this.value = value;
        this.expLevel = expLevel;
    }

    public static ExperienceLevel findDExpLevel(String expLevel) {
        for (ExperienceLevel experienceLevel : ExperienceLevel.values()) {
            if (experienceLevel.expLevel.equals(expLevel))
                return experienceLevel;
        }
        return ExperienceLevel.findDExpLevel(expLevel);
    }

}