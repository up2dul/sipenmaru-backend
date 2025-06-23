package group5.sipenmaru.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyPaymentRequest {
    @NotBlank
    private String status;  // Should be VERIFIED or REJECTED
    
    private String note;    // Optional verification note
}