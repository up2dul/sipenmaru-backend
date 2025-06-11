package group5.sipenmaru.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import group5.sipenmaru.entity.Applicant;
import group5.sipenmaru.entity.Selection;

@Repository
public interface SelectionRepository extends JpaRepository<Selection, Long> {
    Optional<Selection> findByApplicant(Applicant applicant);
} 