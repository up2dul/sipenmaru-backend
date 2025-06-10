package group5.sipenmaru.service;

import group5.sipenmaru.entity.User;
import group5.sipenmaru.model.RegisterUserRequest;
import group5.sipenmaru.repository.UserRepository;
import group5.sipenmaru.security.BCrypt;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void register(RegisterUserRequest request) {
        validationService.validate(request);

        if (userRepository.existsById(Integer.valueOf(request.getEmail()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setRole("USER");
        userRepository.save(user);
    }
}
