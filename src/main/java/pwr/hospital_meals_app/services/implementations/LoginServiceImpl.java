package pwr.hospital_meals_app.services.implementations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.additionals.PasswordChangeDto;
import pwr.hospital_meals_app.dto.additionals.TokensDto;
import pwr.hospital_meals_app.dto.base.LoginDto;
import pwr.hospital_meals_app.persistance.entities.EmployeeEntity;
import pwr.hospital_meals_app.persistance.entities.LoginEntity;
import pwr.hospital_meals_app.persistance.repositories.EmployeeRepository;
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
    private final EmployeeRepository employeeRepository;

    @Value("${jwt-secret-key}")
    private String SECRET_AUTH;

    @Value("${refresh-token-secret-key}")
    private String SECRET_REFRESH;

    public LoginServiceImpl(LoginRepository repository,
                            LoginMapper mapper,
                            BCryptPasswordEncoder bCryptPasswordEncoder,
                            UserDetailsServiceImpl userDetailsService,
                            EmployeeRepository employeeRepository) {
        super(repository, mapper);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDetailsService = userDetailsService;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public boolean changePassword(Integer id, PasswordChangeDto dto) {

        Optional<LoginEntity> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new EntityNotFoundException();
        }

        if (bCryptPasswordEncoder.matches(dto.getOldPassword(), user.get().getPassword())) {
            user.get().setPassword(bCryptPasswordEncoder.encode(dto.getNewPassword()));
            repository.save(user.get());
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean changePasswordForce(Integer id, String newPassword) {

        Optional<EmployeeEntity> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            throw new EntityNotFoundException();
        }
        LoginEntity user = employee.get().getLogin();

        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        repository.save(user);
        return true;
    }

    @Override
    public boolean existsByUsername(String username) {

        return repository.existsByUsername(username);

    }

    @Override
    public Optional<Integer> getUserLoginId(String token) {

        Jws<Claims> claimsJws =
                Jwts.parser()
                        .setSigningKey(SECRET_AUTH.getBytes())
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""));

        return Optional.ofNullable(repository.findByUsername(claimsJws.getBody().getSubject()).getId());
    }

    @Override
    public TokensDto refresh(String refreshToken) {
        String username = getUsernameFromRefreshToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        GrantedAuthority authority = userDetails.getAuthorities().stream().findFirst()
                .orElseThrow(SecurityException::new);
        long currentTimeMillis = System.currentTimeMillis();
        TokensDto tokens = new TokensDto();
        tokens.setJwt("Bearer " + Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("role", authority.getAuthority())
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(currentTimeMillis + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_AUTH.getBytes())
                .compact());
        tokens.setRefreshToken(refreshToken);
        return tokens;
    }


    @Override
    public String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }


    private String getUsernameFromRefreshToken(String refreshToken) {

        Jws<Claims> claimsJws =
                Jwts.parser()
                        .setSigningKey(SECRET_REFRESH.getBytes())
                        .parseClaimsJws(refreshToken);

        return claimsJws.getBody().getSubject();
    }
}
