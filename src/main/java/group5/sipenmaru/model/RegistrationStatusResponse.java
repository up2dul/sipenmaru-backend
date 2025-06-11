package group5.sipenmaru.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationStatusResponse {
    private StatusInfo profileSubmission;

    private StatusInfo selection;

    private StatusInfo payment;

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