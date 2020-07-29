package pwr.hospital_meals_app.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pwr.hospital_meals_app.persistance.entities.LoginEntity;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static pwr.hospital_meals_app.security.SecurityConstants.*;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginEntity creds = new ObjectMapper().readValue(request.getInputStream(), LoginEntity.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(), creds.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication auth) {


        long currentTimeMillis = System.currentTimeMillis();
        String tokenAuth =
                Jwts.builder()
                        .setSubject(((User) auth.getPrincipal()).getUsername())
                        .claim("roles", auth.getAuthorities())
                        .setIssuedAt(new Date(currentTimeMillis))
                        .setExpiration(new Date(currentTimeMillis + EXPIRATION_TIME))
                        .signWith(SignatureAlgorithm.HS512, SECRET_AUTH.getBytes())
                        .compact();

        String tokenRefresh = auth.getCredentials().toString(); //TODO encode user identification data,
        // TODO which can be encoded during refresh

        response.addHeader(HEADER_STRING_AUTH, TOKEN_PREFIX + tokenAuth);
        response.addHeader(HEADER_STRING_REFRESH, tokenRefresh);
    }
}
