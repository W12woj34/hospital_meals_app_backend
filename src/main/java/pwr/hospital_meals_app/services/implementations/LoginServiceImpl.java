package pwr.hospital_meals_app.services.implementations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.additionals.PasswordChangeDto;
import pwr.hospital_meals_app.dto.base.LoginDto;
import pwr.hospital_meals_app.persistance.entities.LoginEntity;
import pwr.hospital_meals_app.persistance.repositories.LoginRepository;
import pwr.hospital_meals_app.services.definitions.BaseCrudService;
import pwr.hospital_meals_app.services.definitions.LoginService;
import pwr.hospital_meals_app.services.mappers.LoginMapper;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.Optional;

import static pwr.hospital_meals_app.security.SecurityConstants.*;

@Service
public class LoginServiceImpl
        extends BaseCrudService<LoginDto, LoginEntity, Integer, LoginRepository>
        implements LoginService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserDetailsServiceImpl userDetailsService;

    public LoginServiceImpl(LoginRepository repository,
                            LoginMapper mapper,
                            BCryptPasswordEncoder bCryptPasswordEncoder,
                            UserDetailsServiceImpl userDetailsService) {
        super(repository, mapper);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void changePassword(Integer id, PasswordChangeDto dto) {

        Optional<LoginEntity> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new EntityNotFoundException();
        }

        if (bCryptPasswordEncoder.matches(dto.getOldPassword(), user.get().getPassword())) {
            user.get().setPassword(bCryptPasswordEncoder.encode(dto.getNewPassword()));
            repository.save(user.get());
        } else {
            throw new SecurityException();
        }
    }

    @Override
    public Optional<Integer> getUserId(String token) {
        Jws<Claims> claimsJws =
                Jwts.parser()
                        .setSigningKey(SECRET_AUTH.getBytes())
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""));

        return Optional.ofNullable(repository.findByUsername(claimsJws.getBody().getSubject()).getEmployee().getId());
    }

    @Override
    public String refresh(String refreshToken) {
        String username = getUsernameFromRefreshToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        long currentTimeMillis = System.currentTimeMillis();

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("role", userDetails.getAuthorities())
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(currentTimeMillis + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_AUTH.getBytes())
                .compact();
    }


    @Override
    public String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    private String getUsernameFromRefreshToken(String refreshToken) {
        //todo decrypt refresh token to username
        return null;
    }
}
