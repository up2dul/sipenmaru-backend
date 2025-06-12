package group5.sipenmaru.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import group5.sipenmaru.model.response.PaymentMethodResponse;
import group5.sipenmaru.repository.PaymentRepository;
import group5.sipenmaru.repository.ApplicantRepository;
import group5.sipenmaru.repository.UserRepository;
import group5.sipenmaru.entity.Payment;
import group5.sipenmaru.entity.Applicant;
import group5.sipenmaru.entity.User;
import group5.sipenmaru.entity.enums.PaymentStatus;
import group5.sipenmaru.model.request.SubmitPaymentProofRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private static final Double REGISTRATION_FEE = 500000.0; // Rp500.000 and it's temporary

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private UserRepository userRepository;

    public PaymentMethodResponse getPaymentMethods() {
        List<PaymentMethodResponse.PaymentMethod> methods = Arrays.asList(
            PaymentMethodResponse.PaymentMethod.builder()
                .name("Bank BCA")
                .virtualAccountNumber("1234567890")
                .instructions(Arrays.asList(
                    "Transfer ke rekening BCA",
                    "Gunakan virtual account number di atas",
                    "Simpan bukti transfer"
                ))
                .build(),
            PaymentMethodResponse.PaymentMethod.builder()
                .name("Bank Mandiri")
                .virtualAccountNumber("0987654321")
                .instructions(Arrays.asList(
                    "Transfer ke rekening Mandiri",
                    "Gunakan virtual account number di atas",
                    "Simpan bukti transfer"
                ))
                .build()
        );

        return PaymentMethodResponse.builder()
            .amount(REGISTRATION_FEE)
            .methods(methods)
            .build();
    }

    @Transactional
    public void submitPaymentProof(SubmitPaymentProofRequest request, UserDetails userDetails) {
        // Find the user first
        User user = userRepository.findByEmail(userDetails.getUsername())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Find the applicant by user
        Applicant applicant = applicantRepository.findByUser(user)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Applicant not found"));

        // Get or create payment
        Payment payment = paymentRepository.findByApplicantId(applicant.getId())
            .orElseGet(() -> {
                Payment newPayment = new Payment();
                newPayment.setApplicant(applicant);
                newPayment.setAmount(REGISTRATION_FEE);
                newPayment.setStatus(PaymentStatus.PENDING);
                newPayment.setCreatedAt(new Date());
                return newPayment;
            });

        // Validate payment status
        if (payment.getStatus() == PaymentStatus.COMPLETED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment has already been completed");
        }

        // Update payment details
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPaymentProofUrl(request.getPaymentProofFileUrl());
        payment.setStatus(PaymentStatus.PENDING); // Reset to pending for admin verification
        payment.setNote("Payment proof submitted, waiting for verification");

        // Save payment
        paymentRepository.save(payment);

        // Update applicant payment status
        applicant.setPaymentStatus(PaymentStatus.PENDING);
        applicantRepository.save(applicant);
    }
} 