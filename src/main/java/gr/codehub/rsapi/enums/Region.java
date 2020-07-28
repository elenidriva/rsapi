package gr.codehub.rsapi.enums;

public enum Region {
    Athens(0,"Athens"),
    Central_Greece(1,"Central Greece"),
    Central_Macedonia(2,"Central Macedonia"),
    Crete(3,"Crete"),
    Eastern_Macedonia_and_Thrace(4,"Eastern Macedonia_and Thrace"),
    Epirus(5,"Epirus"),
    Ionian_Islands(6,"Ionian Islands"),
    North_Aegean(7,"North Aegean"),
    Peloponnese(8,"Peloponnese"),
    South_Aegean(9,""),
    Thessaly(10,"South Aegean"),
    Western_Greece(11,"Western Greece"),
    Western_Macedonia(12,"Western Macedonia");

    private int value;
    private String location;

    Region(int value, String location) {
        this.value = value;
        this.location = location;
    }

    public static Region findRegionByLocation(String location) {
        for (Region region : Region.values()) {
            if (region.location.equals(location))
                return region;
        }
        return Region.findRegionByLocation(location);
    }

}
