package pwr.hospital_meals_app.security;

public class SecurityConstants {
  public static final String SECRET_AUTH = "SUPER_SECRET_KEY_TO_GENERATE_JWT";
  public static final String SECRET_REFRESH = "SUPER_SECRET_KEY_TO_GENERATE_REFRESH_TOKEN";
  public static final long EXPIRATION_TIME = 1000 * 60 * 15; // 15 minutes
  public static final long EXPIRATION_TIME_REFRESH = 1000 * 60 * 60 * 24; // 1 day
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING_AUTH = "Authorization";
  public static final String HEADER_STRING_REFRESH = "Refresh";

}
