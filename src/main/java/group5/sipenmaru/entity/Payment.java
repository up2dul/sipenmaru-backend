package group5.sipenmaru.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import group5.sipenmaru.entity.enums.PaymentStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "applicant_id", referencedColumnName = "id")
    private Applicant applicant;

    private Double amount;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_proof_url")
    private String paymentProofUrl;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "created_at")
    private Date createdAt;
} 