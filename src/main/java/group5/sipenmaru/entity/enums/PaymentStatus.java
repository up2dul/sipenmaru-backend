package group5.sipenmaru.entity.enums;

public enum PaymentStatus {
    PENDING,            // Initial state when registration created
    PROOF_SUBMITTED,    // After applicant uploads proof
    VERIFIED,          // After admin verifies
    REJECTED           // If admin rejects proof
}