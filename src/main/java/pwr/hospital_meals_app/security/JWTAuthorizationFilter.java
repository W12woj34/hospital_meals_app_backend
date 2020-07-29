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
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

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

            String unparsedRoles = claimsJws.getBody().get("roles").toString();
            Collection<SimpleGrantedAuthority> roles = parseRoles(unparsedRoles);

            if (claimsJws.getBody().getSubject() != null) {
                return new UsernamePasswordAuthenticationToken(
                        claimsJws.getBody().getSubject(), null, roles);
            }
            return null;
        }
        return null;
    }

    private Collection<SimpleGrantedAuthority> parseRoles(String unparsedRoles) {

        String[] rolesStrings = unparsedRoles.split(";");
        Collection<SimpleGrantedAuthority> roles = new LinkedList<>();
        Arrays.stream(rolesStrings).forEach(s -> roles.add(new SimpleGrantedAuthority(s)));
        return roles;
    }
}
