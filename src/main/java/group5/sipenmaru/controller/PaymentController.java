package group5.sipenmaru.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import group5.sipenmaru.model.response.PaymentMethodResponse;
import group5.sipenmaru.model.request.SubmitPaymentProofRequest;
import group5.sipenmaru.service.PaymentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/registration/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/methods")
    public ResponseEntity<PaymentMethodResponse> getPaymentMethods() {
        return ResponseEntity.ok(paymentService.getPaymentMethods());
    }

    @PostMapping("/proof")
    public ResponseEntity<Void> submitPaymentProof(@RequestBody SubmitPaymentProofRequest request) {
        paymentService.submitPaymentProof(request);
        return ResponseEntity.ok().build();
    }
} 