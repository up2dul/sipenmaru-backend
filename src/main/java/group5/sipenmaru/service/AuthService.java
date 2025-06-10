package group5.sipenmaru.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import group5.sipenmaru.entity.User;
import group5.sipenmaru.model.LoginUserRequest;
import group5.sipenmaru.model.MeResponse;
import group5.sipenmaru.model.RegisterUserRequest;
import group5.sipenmaru.model.TokenResponse;
import group5.sipenmaru.repository.UserRepository;
import group5.sipenmaru.security.BCrypt;
import jakarta.transaction.Transactional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    private Long next30Days() {
        return System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 30; // 30 days
    }

    @Transactional
    public void register(RegisterUserRequest request) {
        validationService.validate(request);

        if (userRepository.existsById(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setRole("USER");
        userRepository.save(user);
    }

    @Transactional
    public TokenResponse login(LoginUserRequest request) {
        validationService.validate(request);

        User user = userRepository.findById(request.getEmail())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email or password is invalid"));

        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email or password is invalid");
        }

        return TokenResponse.builder()
                .token(UUID.randomUUID().toString()) // temporary token (will be replaced with JWT)
                .expiredAt(next30Days())
                .build();
    }

    @Transactional
    public void logout(User user) {
        user.setToken(null);
        user.setTokenExpiredAt(null);
        userRepository.save(user);
    }

    public TokenResponse refreshToken(User user) {
        return TokenResponse.builder()
                .token(UUID.randomUUID().toString()) // temporary token (will be replaced with JWT)
                .expiredAt(next30Days())
                .build();
    }

    public MeResponse getMe(User user) {
        return MeResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
