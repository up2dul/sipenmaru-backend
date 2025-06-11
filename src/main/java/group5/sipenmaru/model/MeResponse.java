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
public class MeResponse {
    private Long id;

    @JsonProperty("full_name")
    private String fullName;

    private String email;

    private String role;
}
