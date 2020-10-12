package pwr.hospital_meals_app.services.definitions;

public enum EVENT_TYPE {

    ORDER_CREATE(1),
    ORDER_UPDATE(2),
    RESTRICTION_CREATE(3),
    RESTRICTION_UPDATE(4),
    STAY_CREATE(5),
    STAY_END(6),
    DIET_UPDATE(7);

    private final int value;

    EVENT_TYPE(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
