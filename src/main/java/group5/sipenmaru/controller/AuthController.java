package group5.sipenmaru.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import group5.sipenmaru.entity.User;
import group5.sipenmaru.model.LoginUserRequest;
import group5.sipenmaru.model.MeResponse;
import group5.sipenmaru.model.RegisterUserRequest;
import group5.sipenmaru.model.TokenResponse;
import group5.sipenmaru.model.WebResponse;
import group5.sipenmaru.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> register(@RequestBody RegisterUserRequest request) {
        authService.register(request);
        return WebResponse.<String>builder()
                .data("OK")
                .success(true)
                .message("User registered successfully")
                .build();
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<TokenResponse> login(@RequestBody LoginUserRequest request) {
        TokenResponse tokenResponse = authService.login(request);
        return WebResponse.<TokenResponse>builder()
                .data(tokenResponse)
                .success(true)
                .message("Login successful")
                .build();
    }

    @DeleteMapping(path = "/logout", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> logout(User user) {
        authService.logout(user);
        return WebResponse.<String>builder()
                .data("OK")
                .success(true)
                .message("Logout successful")
                .build();
    }

    @PostMapping(path = "/refresh", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<TokenResponse> refreshToken(User user) {
        return WebResponse.<TokenResponse>builder()
                .data(authService.refreshToken(user))
                .success(true)
                .message("Token refreshed successfully")
                .build();
    }

    @GetMapping(path = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<MeResponse> getMe(User user) {
        return WebResponse.<MeResponse>builder()
                .data(authService.getMe(user))
                .success(true)
                .message("User fetched successfully")
                .build();
    }
}
