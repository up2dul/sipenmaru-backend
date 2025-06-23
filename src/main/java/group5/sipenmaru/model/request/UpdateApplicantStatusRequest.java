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
public class UpdateApplicantStatusRequest {
    @NotBlank
    @JsonProperty("selection_status")
    private String selectionStatus;
    
    @NotBlank
    @JsonProperty("payment_status")
    private String paymentStatus;
    
    private String note;
} 