package group5.sipenmaru.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import group5.sipenmaru.model.PaymentMethodResponse;
import group5.sipenmaru.model.SubmitPaymentProofRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private static final Double REGISTRATION_FEE = 500000.0; // Rp500.000 and it's temporary

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

    public void submitPaymentProof(SubmitPaymentProofRequest request) {
        // TODO: Implement payment proof submission logic
        // This would typically involve:
        // 1. Validating the payment method
        // 2. Storing the proof image URL
        // 3. Updating the registration status
        // 4. Notifying administrators
    }
} 