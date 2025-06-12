package group5.sipenmaru.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import group5.sipenmaru.entity.User;
import group5.sipenmaru.entity.enums.UserRole;
import group5.sipenmaru.model.request.LoginUserRequest;
import group5.sipenmaru.model.response.MeResponse;
import group5.sipenmaru.model.request.RegisterUserRequest;
import group5.sipenmaru.model.response.TokenResponse;
import group5.sipenmaru.repository.UserRepository;
import group5.sipenmaru.security.BCrypt;
import group5.sipenmaru.security.JwtConfig;
import group5.sipenmaru.security.JwtService;
import jakarta.transaction.Transactional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private JwtConfig jwtConfig;

    @Transactional
    public void register(RegisterUserRequest request) {
        validationService.validate(request);

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered"));

        user.setEmail(request.getEmail());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setRole(UserRole.APPLICANT);
        user.setFullName(request.getFullName());
        user.setToken(null);
        user.setTokenExpiredAt(null);
        userRepository.save(user);
    }

    @Transactional
    public TokenResponse login(LoginUserRequest request) {
        validationService.validate(request);

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not registered"));

        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email or password is invalid");
        }

        String token = jwtService.generateToken(user.getEmail());
        
        // Store token in database
        user.setToken(token);
        user.setTokenExpiredAt(System.currentTimeMillis() + jwtConfig.getExpiration());
        userRepository.save(user);

        return TokenResponse.builder()
                .token(token)
                .expiredAt(System.currentTimeMillis() + jwtConfig.getExpiration())
                .build();
    }

    @Transactional
    public void logout(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        
        user.setToken(null);
        user.setTokenExpiredAt(null);
        userRepository.save(user);
    }

    public TokenResponse refreshToken(UserDetails userDetails) {
        String token = jwtService.generateToken(userDetails.getUsername());
        return TokenResponse.builder()
                .token(token)
                .expiredAt(System.currentTimeMillis() + jwtConfig.getExpiration())
                .build();
    }

    public MeResponse getMe(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return MeResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
