package group5.sipenmaru.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import group5.sipenmaru.entity.Applicant;
import group5.sipenmaru.entity.Biodata;
import group5.sipenmaru.entity.User;
import group5.sipenmaru.entity.enums.PaymentStatus;
import group5.sipenmaru.entity.enums.SelectionStatus;
import group5.sipenmaru.entity.enums.SubmissionStatus;
import group5.sipenmaru.model.response.ProfileResponse;
import group5.sipenmaru.model.response.RegistrationStatusResponse;
import group5.sipenmaru.model.request.SubmitProfileRequest;
import group5.sipenmaru.repository.ApplicantRepository;
import group5.sipenmaru.repository.BiodataRepository;
import group5.sipenmaru.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class RegistrationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private BiodataRepository biodataRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void submitProfile(UserDetails userDetails, SubmitProfileRequest request) {
        validationService.validate(request);

        User user = userRepository.findByEmail(userDetails.getUsername())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Applicant applicant = applicantRepository.findByUser(user)
            .orElseGet(() -> {
                Applicant newApplicant = new Applicant();
                newApplicant.setUser(user);
                newApplicant.setRegistrationCode(UUID.randomUUID().toString().substring(0, 8));
                newApplicant.setPaymentStatus(PaymentStatus.PENDING);
                newApplicant.setSelectionStatus(SelectionStatus.IN_PROGRESS);
                newApplicant.setCreatedAt(new Date());
                return applicantRepository.save(newApplicant);
            });

        Biodata biodata = biodataRepository.findByApplicant(applicant)
            .orElseGet(() -> {
                Biodata newBiodata = new Biodata();
                newBiodata.setApplicant(applicant);
                return newBiodata;
            });

        biodata.setFullName(request.getFullName());
        biodata.setEmail(request.getEmail());
        biodata.setGender(request.getGender());
        biodata.setBirthDate(request.getBirthDate());
        biodata.setSelectedMajor(request.getSelectedMajor());
        biodata.setAddress(request.getAddress());
        biodata.setDiplomaFileUrl(request.getDiplomaFileUrl());

        biodataRepository.save(biodata);
    }

    public ProfileResponse getProfile(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Applicant applicant = applicantRepository.findByUser(user)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Applicant not found"));

        Biodata biodata = biodataRepository.findByApplicant(applicant)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));

        return ProfileResponse.builder()
            .fullName(biodata.getFullName())
            .email(biodata.getEmail())
            .gender(biodata.getGender())
            .birthDate(biodata.getBirthDate())
            .selectedMajor(biodata.getSelectedMajor())
            .address(biodata.getAddress())
            .diplomaFileUrl(biodata.getDiplomaFileUrl())
            .build();
    }

    public RegistrationStatusResponse getStatus(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Applicant applicant = applicantRepository.findByUser(user)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Applicant not found"));

        return RegistrationStatusResponse.builder()
            .profileSubmission(RegistrationStatusResponse.StatusInfo.builder()
                .status(applicant.getBiodata() != null ? SubmissionStatus.COMPLETED.toString() : SubmissionStatus.PENDING.toString())
                .note(applicant.getBiodata() != null ? "Profile submitted" : "Profile not submitted")
                .build())
            .selection(RegistrationStatusResponse.StatusInfo.builder()
                .status(applicant.getSelectionStatus().toString())
                .note(applicant.getSelection() != null ? applicant.getSelection().getNote() : null)
                .build())
            .payment(RegistrationStatusResponse.StatusInfo.builder()
                .status(applicant.getPaymentStatus().toString())
                .note(applicant.getPayment() != null ? applicant.getPayment().getNote() : null)
                .build())
            .paymentVerification(RegistrationStatusResponse.StatusInfo.builder()
                .status(applicant.getPayment() != null ? applicant.getPayment().getStatus().toString() : SubmissionStatus.PENDING.toString())
                .note(applicant.getPayment() != null ? applicant.getPayment().getNote() : null)
                .build())
            .build();
    }
}