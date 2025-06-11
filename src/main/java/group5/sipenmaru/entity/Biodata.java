package group5.sipenmaru.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import group5.sipenmaru.entity.enums.Gender;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "biodata")
public class Biodata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "applicant_id", referencedColumnName = "id")
    private Applicant applicant;

    @Column(name = "full_name")
    private String fullName;

    private String email;

    @Column(name = "birth_date")
    private Date birthDate;

    private String address;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "selected_major")
    private String selectedMajor;

    @Column(name = "diploma_file_url")
    private String diplomaFileUrl;

    @Column(name = "created_at")
    private Date createdAt;
} 