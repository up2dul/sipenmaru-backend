package group5.sipenmaru.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<WebResponse<String>> register(@RequestBody RegisterUserRequest request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(WebResponse.<String>builder()
                        .data("OK")
                        .success(true)
                        .message("User registered successfully")
                        .build());
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

    @PostMapping(path = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> logout(@AuthenticationPrincipal UserDetails userDetails) {
        authService.logout(userDetails);
        return WebResponse.<String>builder()
                .data("OK")
                .success(true)
                .message("Logout successful")
                .build();
    }

    // Temporary disabled
    // @PostMapping(path = "/refresh", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    // public WebResponse<TokenResponse> refreshToken(@AuthenticationPrincipal UserDetails userDetails) {
    //     return WebResponse.<TokenResponse>builder()
    //             .data(authService.refreshToken(userDetails))
    //             .success(true)
    //             .message("Token refreshed successfully")
    //             .build();
    // }

    @GetMapping(path = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<MeResponse> getMe(@AuthenticationPrincipal UserDetails userDetails) {
        MeResponse meResponse = authService.getMe(userDetails);
        return WebResponse.<MeResponse>builder()
                .data(meResponse)
                .success(true)
                .message("User fetched successfully")
                .build();
    }
}
