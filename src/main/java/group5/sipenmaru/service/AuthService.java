package group5.sipenmaru.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import group5.sipenmaru.entity.User;
import group5.sipenmaru.model.LoginUserRequest;
import group5.sipenmaru.model.MeResponse;
import group5.sipenmaru.model.RegisterUserRequest;
import group5.sipenmaru.model.TokenResponse;
import group5.sipenmaru.repository.UserRepository;
import group5.sipenmaru.security.BCrypt;
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

    @Transactional
    public void register(RegisterUserRequest request) {
        validationService.validate(request);

        if (userRepository.existsById(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setRole("APPLICANT");
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

        String token = jwtService.generateToken(user.getEmail());
        return TokenResponse.builder()
                .token(token)
                .expiredAt(System.currentTimeMillis() + 86400000) // 24 hours
                .build();
    }

    @Transactional
    public void logout(UserDetails userDetails) {
        User user = userRepository.findById(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setToken(null);
        user.setTokenExpiredAt(null);
        userRepository.save(user);
    }

    public TokenResponse refreshToken(UserDetails userDetails) {
        String token = jwtService.generateToken(userDetails.getUsername());
        return TokenResponse.builder()
                .token(token)
                .expiredAt(System.currentTimeMillis() + 86400000) // 24 hours
                .build();
    }

    public MeResponse getMe(UserDetails userDetails) {
        User user = userRepository.findById(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return MeResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
