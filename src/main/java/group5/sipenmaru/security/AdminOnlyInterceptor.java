package group5.sipenmaru.security;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import group5.sipenmaru.entity.User;
import group5.sipenmaru.entity.enums.UserRole;
import group5.sipenmaru.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AdminOnlyInterceptor implements HandlerInterceptor {
    private final UserRepository userRepository;

    public AdminOnlyInterceptor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        AdminOnly adminOnly = handlerMethod.getMethodAnnotation(AdminOnly.class);
        if (adminOnly == null) {
            adminOnly = handlerMethod.getBeanType().getAnnotation(AdminOnly.class);
        }

        if (adminOnly != null) {
            String email = request.getUserPrincipal().getName();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

            if (user.getRole() != UserRole.ADMIN) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied. Admin role required.");
            }
        }

        return true;
    }
} 