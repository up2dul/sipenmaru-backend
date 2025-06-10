package group5.sipenmaru.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(unique = true, nullable = false)
    private String id;

    @Column(name = "full_name")
    private String fullName;

    private String email;

    private String password;

    private String role;

    private String token;

    @Column(name = "token_expired_at")
    private Long tokenExpiredAt;
}
