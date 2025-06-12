package group5.sipenmaru.model.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import group5.sipenmaru.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponse {
    @JsonProperty("full_name")
    private String fullName;

    private String email;

    private Gender gender;

    @JsonProperty("full_name")
    private Date birthDate;

    @JsonProperty("selected_major")
    private String selectedMajor;

    private String address;

    @JsonProperty("diploma_file_url")
    private String diplomaFileUrl;
} 