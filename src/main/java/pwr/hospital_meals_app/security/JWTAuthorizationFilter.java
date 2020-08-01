package pwr.hospital_meals_app.security;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static pwr.hospital_meals_app.security.SecurityConstants.*;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader(HEADER_STRING_AUTH);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication;
        try {
            authentication = getAuthentication(request);
        } catch (SignatureException | ExpiredJwtException e) {
            response.setStatus(401);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING_AUTH);
        if (token != null) {

            Jws<Claims> claimsJws =
                    Jwts.parser()
                            .setSigningKey(SECRET_AUTH.getBytes())
                            .parseClaimsJws(token.replace(TOKEN_PREFIX, ""));

            SimpleGrantedAuthority role = new SimpleGrantedAuthority(claimsJws.getBody().get("role").toString());

            if (claimsJws.getBody().getSubject() != null) {
                return new UsernamePasswordAuthenticationToken(
                        claimsJws.getBody().getSubject(), null, Collections.singletonList(role));
            }
            return null;
        }
        return null;
    }

}
