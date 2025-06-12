package group5.sipenmaru.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationStatusResponse {
    @JsonProperty("profile_submission")
    private StatusInfo profileSubmission;

    private StatusInfo selection;

    private StatusInfo payment;

    @JsonProperty("payment_verification")
    private StatusInfo paymentVerification;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class StatusInfo {
        private String status;
        private String note;
    }
} 