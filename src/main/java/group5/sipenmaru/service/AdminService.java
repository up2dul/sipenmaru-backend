package group5.sipenmaru.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import group5.sipenmaru.entity.Applicant;
import group5.sipenmaru.entity.Biodata;
import group5.sipenmaru.entity.Payment;
import group5.sipenmaru.entity.Selection;
import group5.sipenmaru.entity.enums.Gender;
import group5.sipenmaru.entity.enums.PaymentStatus;
import group5.sipenmaru.entity.enums.SelectionStatus;
import group5.sipenmaru.model.ApplicantDetailResponse;
import group5.sipenmaru.model.ApplicantListResponse;
import group5.sipenmaru.model.DashboardResponse;
import group5.sipenmaru.model.UpdateApplicantStatusRequest;
import group5.sipenmaru.repository.ApplicantRepository;
import group5.sipenmaru.repository.BiodataRepository;
import group5.sipenmaru.repository.PaymentRepository;
import group5.sipenmaru.repository.SelectionRepository;
import jakarta.transaction.Transactional;

@Service
public class AdminService {
    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private BiodataRepository biodataRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private SelectionRepository selectionRepository;

    public DashboardResponse getDashboard() {
        List<Applicant> allApplicants = applicantRepository.findAll();
        
        // Get current month's start and end dates
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startOfMonth = calendar.getTime();
        
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.SECOND, -1);
        Date endOfMonth = calendar.getTime();

        // Get this month's applicants
        List<Applicant> thisMonthApplicants = allApplicants.stream()
            .filter(applicant -> {
                Date createdAt = applicant.getCreatedAt();
                return createdAt.after(startOfMonth) && createdAt.before(endOfMonth);
            })
            .collect(Collectors.toList());

        // Get recent applicants (last 3)
        List<DashboardResponse.RecentApplicant> recentApplicants = allApplicants.stream()
            .sorted((a1, a2) -> a2.getCreatedAt().compareTo(a1.getCreatedAt()))
            .limit(3)
            .map(applicant -> {
                Biodata biodata = biodataRepository.findByApplicant(applicant)
                    .orElse(null);
                return DashboardResponse.RecentApplicant.builder()
                    .fullName(biodata != null ? biodata.getFullName() : "Unknown")
                    .build();
            })
            .collect(Collectors.toList());

        // Calculate gender stats
        int maleCount = 0;
        int femaleCount = 0;
        for (Applicant applicant : allApplicants) {
            Biodata biodata = biodataRepository.findByApplicant(applicant).orElse(null);
            if (biodata != null) {
                if (biodata.getGender() == Gender.MALE) {
                    maleCount++;
                } else if (biodata.getGender() == Gender.FEMALE) {
                    femaleCount++;
                }
            }
        }

        return DashboardResponse.builder()
            .totalApplicants(allApplicants.size())
            .totalApplicantsThisMonth(thisMonthApplicants.size())
            .recentApplicants(recentApplicants)
            .genderStats(DashboardResponse.GenderStats.builder()
                .male(maleCount)
                .female(femaleCount)
                .build())
            .build();
    }

    public List<ApplicantListResponse> getApplicants() {
        List<Applicant> applicants = applicantRepository.findAll();
        return applicants.stream()
            .map(applicant -> {
                Biodata biodata = biodataRepository.findByApplicant(applicant).orElse(null);
                return ApplicantListResponse.builder()
                    .id(applicant.getId())
                    .fullName(biodata != null ? biodata.getFullName() : "Unknown")
                    .email(applicant.getUser().getEmail())
                    .paymentStatus(applicant.getPaymentStatus().toString())
                    .selectionStatus(applicant.getSelectionStatus().toString())
                    .build();
            })
            .collect(Collectors.toList());
    }

    public ApplicantDetailResponse getApplicantDetail(Long id) {
        Applicant applicant = applicantRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Applicant not found"));

        Biodata biodata = biodataRepository.findByApplicant(applicant)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Biodata not found"));

        Payment payment = paymentRepository.findByApplicant(applicant).orElse(null);
        Selection selection = selectionRepository.findByApplicant(applicant).orElse(null);

        return ApplicantDetailResponse.builder()
            .id(applicant.getId())
            .registrationCode(applicant.getRegistrationCode())
            .fullName(biodata.getFullName())
            .email(applicant.getUser().getEmail())
            .gender(biodata.getGender().toString())
            .birthDate(biodata.getBirthDate())
            .selectedMajor(biodata.getSelectedMajor())
            .address(biodata.getAddress())
            .registrationFileUrl(biodata.getDiplomaFileUrl())
            .paymentProofUrl(payment != null ? payment.getPaymentProofUrl() : null)
            .paymentStatus(applicant.getPaymentStatus().toString())
            .selectionStatus(applicant.getSelectionStatus().toString())
            .note(selection != null ? selection.getNote() : null)
            .build();
    }

    @Transactional
    public void updateApplicantStatus(Long id, UpdateApplicantStatusRequest request) {
        Applicant applicant = applicantRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Applicant not found"));

        // Update payment status
        applicant.setPaymentStatus(PaymentStatus.valueOf(request.getPaymentStatus()));

        // Update selection status
        applicant.setSelectionStatus(SelectionStatus.valueOf(request.getSelectionStatus()));

        // Update or create selection record with note
        Selection selection = selectionRepository.findByApplicant(applicant)
            .orElse(new Selection());
        selection.setApplicant(applicant);
        selection.setStatus(SelectionStatus.valueOf(request.getSelectionStatus()));
        selection.setNote(request.getNote());
        selectionRepository.save(selection);

        applicantRepository.save(applicant);
    }
} 