package group5.sipenmaru.model;

import java.util.Date;

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
    private String fullName;

    private String email;

    private Gender gender;

    private Date birthDate;

    private String selectedMajor;

    private String address;

    private String diplomaFileUrl;
} 