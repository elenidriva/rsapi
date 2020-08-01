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

    /**
     * This method takes each exp level, takes the value(int) and sees if it is the same
     *
     * @param expLevel to be able to do matching with the strings and values
     * @return the experience level to match
     */
    public static ExperienceLevel findDExpLevel(String expLevel) {
        for (ExperienceLevel experienceLevel : ExperienceLevel.values()) {
            if (experienceLevel.expLevel.equals(expLevel))
                return experienceLevel;
        }
        return ExperienceLevel.findDExpLevel(expLevel);
    }

}