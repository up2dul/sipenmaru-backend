package group5.sipenmaru.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmitPaymentProofRequest {
    private String paymentMethod;

    private String proofImageUrl;
}