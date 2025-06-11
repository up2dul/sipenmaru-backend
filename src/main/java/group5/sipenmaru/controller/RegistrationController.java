package group5.sipenmaru.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import group5.sipenmaru.model.ProfileResponse;
import group5.sipenmaru.model.RegistrationStatusResponse;
import group5.sipenmaru.model.SubmitProfileRequest;
import group5.sipenmaru.model.WebResponse;
import group5.sipenmaru.service.RegistrationService;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @PostMapping(path = "/profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> submitProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody SubmitProfileRequest request) {
        registrationService.submitProfile(userDetails, request);
        return WebResponse.<String>builder()
                .data("OK")
                .success(true)
                .message("Profile submitted successfully, waiting for admin verification")
                .build();
    }

    @GetMapping(path = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<ProfileResponse> getProfile(
            @AuthenticationPrincipal UserDetails userDetails) {
        ProfileResponse profile = registrationService.getProfile(userDetails);
        return WebResponse.<ProfileResponse>builder()
                .data(profile)
                .success(true)
                .message("Profile data fetched successfully")
                .build();
    }

    @GetMapping(path = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<RegistrationStatusResponse> getStatus(
            @AuthenticationPrincipal UserDetails userDetails) {
        RegistrationStatusResponse status = registrationService.getStatus(userDetails);
        return WebResponse.<RegistrationStatusResponse>builder()
                .data(status)
                .success(true)
                .message("Registration status fetched successfully")
                .build();
    }
}