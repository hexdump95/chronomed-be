package ar.sergiovillanueva.chronomed.repository;

import ar.sergiovillanueva.chronomed.entity.PatientInsurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientInsuranceRepository extends JpaRepository<PatientInsurance, Long> {
}
