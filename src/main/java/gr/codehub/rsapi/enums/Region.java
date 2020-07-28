package gr.codehub.rsapi.enums;

public enum Region {
    Attica(0),
    Central_Greece(1),
    Central_Macedonia(2),
    Crete(3),
    Eastern_Macedonia_and_Thrace(4),
    Epirus(5),
    Ionian_Islands(6),
    North_Aegean(7),
    Peloponnese(8),
    South_Aegean(9),
    Thessaly(10),
    Western_Greece(11),
    Western_Macedonia(12);

    private int value;

    Region(int value) {
        this.value = value;
    }


}
