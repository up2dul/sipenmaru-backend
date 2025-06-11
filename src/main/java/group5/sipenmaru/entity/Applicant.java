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

import group5.sipenmaru.entity.enums.PaymentStatus;
import group5.sipenmaru.entity.enums.SelectionStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "applicants")
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "registration_code")
    private String registrationCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "selection_status")
    private SelectionStatus selectionStatus;

    @OneToOne(mappedBy = "applicant")
    private Biodata biodata;

    @OneToOne(mappedBy = "applicant")
    private Selection selection;

    @OneToOne(mappedBy = "applicant")
    private Payment payment;

    @Column(name = "created_at")
    private Date createdAt;
}
