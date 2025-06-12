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
public class UpdateApplicantStatusRequest {
    @NotBlank
    private String selectionStatus;
    
    @NotBlank
    private String paymentStatus;
    
    private String note;
} 