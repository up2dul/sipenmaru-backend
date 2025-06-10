package group5.sipenmaru.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import group5.sipenmaru.model.LoginUserRequest;
import group5.sipenmaru.model.TokenResponse;
import group5.sipenmaru.model.WebResponse;
import group5.sipenmaru.service.AuthService;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping(
        path = "/api/auth/login",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<TokenResponse> login(@RequestBody LoginUserRequest request) {
        TokenResponse tokenResponse = authService.login(request);
        return WebResponse.<TokenResponse>builder()
                .data(tokenResponse)
                .success(true)
                .message("Login successful")
                .build();
    }
}
