package group5.sipenmaru.controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import group5.sipenmaru.model.response.PaymentMethodResponse;
import group5.sipenmaru.model.response.WebResponse;
import group5.sipenmaru.model.request.SubmitPaymentProofRequest;
import group5.sipenmaru.service.PaymentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/registration/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping(path = "/methods", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<PaymentMethodResponse> getPaymentMethods() {
        return WebResponse.<PaymentMethodResponse>builder()
                .data(paymentService.getPaymentMethods())
                .success(true)
                .message("Payment methods fetched successfully")
                .build();
    }

    @PostMapping(path = "/proof", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> submitPaymentProof(@RequestBody SubmitPaymentProofRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        paymentService.submitPaymentProof(request, userDetails);
        return WebResponse.<String>builder()
                .data("OK")
                .success(true)
                .message("Payment proof uploaded successfully, waiting for verification")
                .build();
    }
} 