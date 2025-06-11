package group5.sipenmaru.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import group5.sipenmaru.model.*;
import group5.sipenmaru.service.AdminService;
import lombok.RequiredArgsConstructor;
import group5.sipenmaru.security.AdminOnly;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@AdminOnly
public class AdminController {
    private final AdminService adminService;

    @GetMapping(path = "/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<DashboardResponse> getDashboard(
            @AuthenticationPrincipal UserDetails userDetails) {
        DashboardResponse dashboard = adminService.getDashboard();
        return WebResponse.<DashboardResponse>builder()
                .data(dashboard)
                .success(true)
                .message("Dashboard statistics fetched successfully")
                .build();
    }

    @GetMapping(path = "/applicants", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<ApplicantListResponse>> getApplicants(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<ApplicantListResponse> applicants = adminService.getApplicants();
        return WebResponse.<List<ApplicantListResponse>>builder()
                .data(applicants)
                .success(true)
                .message("Applicants list fetched successfully")
                .build();
    }

    @GetMapping(path = "/applicants/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<ApplicantDetailResponse> getApplicantDetail(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("id") Long id) {
        ApplicantDetailResponse applicant = adminService.getApplicantDetail(id);
        return WebResponse.<ApplicantDetailResponse>builder()
                .data(applicant)
                .success(true)
                .message("Applicant detail fetched successfully")
                .build();
    }

    @PutMapping(path = "/applicants/{id}/status", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> updateApplicantStatus(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("id") Long id,
            @RequestBody UpdateApplicantStatusRequest request) {
        adminService.updateApplicantStatus(id, request);
        return WebResponse.<String>builder()
                .data("OK")
                .success(true)
                .message("Applicant status updated successfully")
                .build();
    }
} 