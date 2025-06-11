package group5.sipenmaru.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import group5.sipenmaru.entity.Applicant;
import group5.sipenmaru.entity.Biodata;
import group5.sipenmaru.entity.Payment;
import group5.sipenmaru.entity.User;
import group5.sipenmaru.entity.enums.Gender;
import group5.sipenmaru.entity.enums.PaymentStatus;
import group5.sipenmaru.entity.enums.SelectionStatus;
import group5.sipenmaru.entity.enums.UserRole;
import group5.sipenmaru.repository.ApplicantRepository;
import group5.sipenmaru.repository.BiodataRepository;
import group5.sipenmaru.repository.PaymentRepository;
import group5.sipenmaru.repository.UserRepository;
import group5.sipenmaru.security.BCrypt;

import java.util.Date;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private final String SEED_PASSWORD = "user123";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private BiodataRepository biodataRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public void run(String... args) throws Exception {
        // Only seed if no users exist
        if (userRepository.count() > 0) {
            return;
        }

        // Create admin user
        User admin = new User();
        admin.setFullName("Admin Sipenmaru");
        admin.setEmail("admin@sipenmaru.com");
        admin.setPassword(BCrypt.hashpw(SEED_PASSWORD, BCrypt.gensalt()));
        admin.setRole(UserRole.ADMIN);
        userRepository.save(admin);

        // Create applicant users
        User doni = createUser("Doni Rubiagatra", "doni@sipenmaru.com", SEED_PASSWORD);
        User zain = createUser("Zain Fathoni", "zain@sipenmaru.com", SEED_PASSWORD);
        User ariya = createUser("Ariya Hidayat", "ariya@sipenmaru.com", SEED_PASSWORD);

        // Create applicants
        Applicant doniApplicant = createApplicant(doni, "REG-2024-001", PaymentStatus.PAID, SelectionStatus.PASSED);
        Applicant zainApplicant = createApplicant(zain, "REG-2024-002", PaymentStatus.PAID, SelectionStatus.IN_PROGRESS);
        Applicant ariyaApplicant = createApplicant(ariya, "REG-2024-003", PaymentStatus.UNPAID, SelectionStatus.IN_PROGRESS);

        // Create biodata
        createBiodata(doniApplicant, "Doni Rubiagatra", "doni@sipenmaru.com", "2000-05-15", "Jl. Merdeka No. 123, Jakarta", Gender.MALE, "Teknik Informatika");
        createBiodata(zainApplicant, "Zain Fathoni", "zain@sipenmaru.com", "2001-08-20", "Jl. Sudirman No. 45, Bandung", Gender.FEMALE, "Sistem Informasi");
        createBiodata(ariyaApplicant, "Ariya Hidayat", "ariya@sipenmaru.com", "2000-11-10", "Jl. Gatot Subroto No. 78, Surabaya", Gender.MALE, "Teknik Komputer");

        // Create payments
        createPayment(doniApplicant, 500000.0, "BANK_TRANSFER", "https://example.com/payments/doni-proof.jpg", PaymentStatus.PAID);
        createPayment(zainApplicant, 500000.0, "BANK_TRANSFER", "https://example.com/payments/zain-proof.jpg", PaymentStatus.PAID);
        createPayment(ariyaApplicant, 500000.0, "BANK_TRANSFER", null, PaymentStatus.UNPAID);
    }

    private User createUser(String fullName, String email, String password) {
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        user.setRole(UserRole.APPLICANT);
        return userRepository.save(user);
    }

    private Applicant createApplicant(User user, String registrationCode, PaymentStatus paymentStatus, SelectionStatus selectionStatus) {
        Applicant applicant = new Applicant();
        applicant.setUser(user);
        applicant.setRegistrationCode(registrationCode);
        applicant.setPaymentStatus(paymentStatus);
        applicant.setSelectionStatus(selectionStatus);
        applicant.setCreatedAt(new Date());
        return applicantRepository.save(applicant);
    }

    private void createBiodata(Applicant applicant, String fullName, String email, String birthDate, String address, Gender gender, String selectedMajor) {
        Biodata biodata = new Biodata();
        biodata.setApplicant(applicant);
        biodata.setFullName(fullName);
        biodata.setEmail(email);
        biodata.setBirthDate(parseDate(birthDate));
        biodata.setAddress(address);
        biodata.setGender(gender);
        biodata.setSelectedMajor(selectedMajor);
        biodata.setCreatedAt(new Date());
        biodataRepository.save(biodata);
    }

    private void createPayment(Applicant applicant, Double amount, String paymentMethod, String paymentProofUrl, PaymentStatus status) {
        Payment payment = new Payment();
        payment.setApplicant(applicant);
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentProofUrl(paymentProofUrl);
        payment.setStatus(status);
        payment.setCreatedAt(new Date());
        paymentRepository.save(payment);
    }

    private Date parseDate(String dateStr) {
        try {
            return new java.text.SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing date: " + dateStr, e);
        }
    }
} 