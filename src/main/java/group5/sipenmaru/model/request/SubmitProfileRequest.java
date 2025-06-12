package group5.sipenmaru.model.request;

import java.util.Date;

import group5.sipenmaru.entity.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmitProfileRequest {
    @NotBlank
    private String fullName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    @Past
    private Date birthDate;

    @NotBlank
    private String selectedMajor;

    @NotBlank
    private String address;

    @NotBlank
    private String diplomaFileUrl;
} 