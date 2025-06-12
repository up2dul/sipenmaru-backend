package group5.sipenmaru.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicantListResponse {
    private Long id;

    @JsonProperty("full_name")
    private String fullName;

    private String email;

    @JsonProperty("payment_status")
    private String paymentStatus;

    @JsonProperty("selection_status")
    private String selectionStatus;
} 