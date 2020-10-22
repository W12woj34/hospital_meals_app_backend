package pwr.hospital_meals_app.security;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pwr.hospital_meals_app.services.implementations.UserDetailsServiceImpl;

import static pwr.hospital_meals_app.controllers.RestMappings.*;
import static pwr.hospital_meals_app.security.SecurityConstants.*;


@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private final UserDetailsServiceImpl userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(
            UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        DIET + "/**",
                        EMPLOYEE + "/**",
                        PATIENT + "/**",
                        STAY + "/**",
                        WARD + "/**",
                        PATIENT_DIET + "/**",
                        PERSON + "/**",
                        WARD + "/**")
                .hasAnyRole(ROLE_NURSE, ROLE_DIETITIAN, ROLE_KITCHEN, ROLE_MOVEMENT)
                .antMatchers(PERSON + "/**")
                .hasAnyRole(ROLE_NURSE, ROLE_MOVEMENT)
                .antMatchers(MEAL + "/**",
                        MEAL_TYPE + "/**",
                        ORDER + "/**",
                        ORDER_STATUS + "/**",
                        STAY + "/**")
                .hasRole(ROLE_NURSE)
                .antMatchers(PATIENT + "/**")
                .hasAnyRole(ROLE_NURSE, ROLE_DIETITIAN)
                .antMatchers(HttpMethod.GET,
                        RESTRICTION_STATUS + "/**")
                .hasRole(ROLE_DIETITIAN)
                .antMatchers(PATIENT_DIET + "/**",
                        DIETARY_RESTRICTIONS + "/**")
                .hasRole(ROLE_DIETITIAN)
                .antMatchers(HttpMethod.GET,
                        EVENT + "/**",
                        LOG + "/**")
                .hasRole(ROLE_MOVEMENT)
                .antMatchers(MAIN_KITCHEN_DIETITIAN + "/**",
                        DIETITIAN + "/**",
                        PATIENT_MOVEMENT + "/**",
                        WARD_NURSE + "/**",
                        EMPLOYEE + "/**")
                .hasRole(ROLE_MOVEMENT)
                .antMatchers(DIET + "/**",
                        WARD + "/**",
                        EVENT + "/**",
                        LOG + "/**",
                        RESTRICTION_STATUS + "/**")
                .denyAll()
                .antMatchers(LOGIN + CHANGE_PASSWORD,
                        LOGIN + REFRESH_TOKEN)
                .hasAnyRole(ROLE_NURSE, ROLE_DIETITIAN, ROLE_KITCHEN, ROLE_MOVEMENT)
                .antMatchers(LOGIN + "/**")
                .hasRole(ROLE_MOVEMENT)
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "https://localhost:4200"));
        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        corsConfiguration.setExposedHeaders(
                Arrays.asList(
                        HEADER_STRING_AUTH,
                        HEADER_STRING_REFRESH,
                        HEADER_STRING_ROLE
                ));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}
