package group5.sipenmaru.model.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicantDetailResponse {
    private Long id;

    @JsonProperty("registration_code")
    private String registrationCode;

    @JsonProperty("full_name")
    private String fullName;

    private String email;

    private String gender;

    @JsonProperty("birth_date")
    private Date birthDate;

    @JsonProperty("selected_major")
    private String selectedMajor;

    private String address;

    @JsonProperty("registration_file_url")
    private String registrationFileUrl;

    @JsonProperty("payment_proof_url")
    private String paymentProofUrl;

    @JsonProperty("payment_status")
    private String paymentStatus;

    @JsonProperty("selection_status")
    private String selectionStatus;

    private String note;
} 