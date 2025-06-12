package group5.sipenmaru.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmitPaymentProofRequest {
    @NotBlank
    @JsonProperty("payment_method")
    private String paymentMethod;

    @NotBlank
    @JsonProperty("payment_proof_file_url")
    private String paymentProofFileUrl;
}