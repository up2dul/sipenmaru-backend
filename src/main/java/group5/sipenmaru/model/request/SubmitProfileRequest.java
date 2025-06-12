package group5.sipenmaru.model.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import group5.sipenmaru.entity.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(min = 3, max = 100)
    @JsonProperty("full_name")
    private String fullName;

    @NotBlank
    @Email(message = "Invalid email address")
    @JsonProperty("email")
    private String email;

    @NotNull
    @JsonProperty("gender")
    private Gender gender;

    @NotNull
    @JsonProperty("birth_date")
    private Date birthDate;

    @NotBlank
    @JsonProperty("selected_major")
    private String selectedMajor;

    @NotBlank
    @JsonProperty("address")
    private String address;

    @NotBlank
    @JsonProperty("diploma_file_url")
    private String diplomaFileUrl;
} 