package group5.sipenmaru.model;

import java.util.Date;
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
    private String registrationCode;
    private String fullName;
    private String email;
    private String gender;
    private Date birthDate;
    private String selectedMajor;
    private String address;
    private String registrationFileUrl;
    private String paymentProofUrl;
    private String paymentStatus;
    private String selectionStatus;
    private String note;
} 