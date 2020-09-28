package pwr.hospital_meals_app.controllers;

/**
 * Class containing constants with API routes
 */
public final class RestMappings {

    public static final String ID = "/{id}";
    public static final String SEARCH = "/search";
    public static final String PERSONAL = "/personal";

    public static final String DIET = "/diets";
    public static final String DIETARY_RESTRICTIONS = "/dietary-restrictions";
    public static final String DIETITIAN = "/dietitians";
    public static final String EMPLOYEE = "/employees";
    public static final String EVENT = "/events";
    public static final String LOG = "/logs";
    public static final String LOGIN = "/logins";
    public static final String MAIN_KITCHEN_DIETITIAN = "/main-kitchen-dietitians";
    public static final String MEAL = "/meals";
    public static final String MEAL_TYP = "/meal-types";
    public static final String ORDER = "/orders";
    public static final String ORDER_STATUS = "/order-statuses";
    public static final String PATIENT = "/patients";
    public static final String PATIENT_DIET = "/patients-diets";
    public static final String PATIENT_MOVEMENT = "/patient-movements";
    public static final String PERSON = "/persons";
    public static final String RESTRICTION_STATUS = "/restriction-statuses";
    public static final String STAY = "/stays";
    public static final String WARD = "/wards";
    public static final String WARD_NURSE = "/ward-nurses";

    public static final String SIGN_UP = "/sign-up";
    public static final String REFRESH_TOKEN = "/refresh";
    public static final String CHANGE_PASSWORD = "change-password";
    public static final String CHANGE_PASSWORD_FORCE = "change-password-force";

    private RestMappings() {
    }
}
