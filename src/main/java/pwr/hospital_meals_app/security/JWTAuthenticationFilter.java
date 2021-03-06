package pwr.hospital_meals_app.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pwr.hospital_meals_app.persistance.entities.LoginEntity;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static pwr.hospital_meals_app.security.SecurityConstants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final SecurityKeys keys;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
                                   SecurityKeys keys) {
        this.authenticationManager = authenticationManager;
        this.keys = keys;
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

        String role = resolveRole(auth.getAuthorities());

        long currentTimeMillis = System.currentTimeMillis();
        String tokenAuth =
                Jwts.builder()
                        .setSubject(((User) auth.getPrincipal()).getUsername())
                        .claim("role", role)
                        .setIssuedAt(new Date(currentTimeMillis))
                        .setExpiration(new Date(currentTimeMillis + EXPIRATION_TIME))
                        .signWith(SignatureAlgorithm.HS512, keys.getSECRET_AUTH().getBytes())
                        .compact();

        String tokenRefresh =
                Jwts.builder()
                        .setSubject(((User) auth.getPrincipal()).getUsername())
                        .setIssuedAt(new Date(currentTimeMillis))
                        .setExpiration(new Date(currentTimeMillis + EXPIRATION_TIME_REFRESH))
                        .signWith(SignatureAlgorithm.HS512, keys.getSECRET_REFRESH().getBytes())
                        .compact();

        response.addHeader(HEADER_STRING_ROLE, role);
        response.addHeader(HEADER_STRING_AUTH, TOKEN_PREFIX + tokenAuth);
        response.addHeader(HEADER_STRING_REFRESH, tokenRefresh);
    }

    private String resolveRole(Collection<? extends GrantedAuthority> authorities) {

        if (authorities.stream().findFirst().isPresent()) {
            return authorities.stream().findFirst().get().getAuthority();
        }
        throw new SecurityException();
    }
}
