package group5.sipenmaru.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @Column(unique = true, nullable = false)
    private String id;

    @OneToOne
    @JoinColumn(name = "applicant_id", referencedColumnName = "id")
    private Applicant applicant;

    private Double amount;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_proof_url")
    private String paymentProofUrl;

    private String status;

    @Column(name = "created_at")
    private Date createdAt;
} 