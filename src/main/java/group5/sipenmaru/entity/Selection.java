package group5.sipenmaru.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import group5.sipenmaru.entity.enums.SelectionStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "selections")
public class Selection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "applicant_id", referencedColumnName = "id")
    private Applicant applicant;

    @Enumerated(EnumType.STRING)
    private SelectionStatus status;

    private String note;

    @Column(name = "created_at")
    private Date createdAt;
} 