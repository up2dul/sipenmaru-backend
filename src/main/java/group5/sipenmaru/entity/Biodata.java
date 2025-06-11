package group5.sipenmaru.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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