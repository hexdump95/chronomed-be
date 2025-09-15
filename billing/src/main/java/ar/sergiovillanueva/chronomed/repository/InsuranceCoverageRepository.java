package ar.sergiovillanueva.chronomed.repository;

import ar.sergiovillanueva.chronomed.entity.InsuranceCoverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceCoverageRepository extends JpaRepository<InsuranceCoverage, Long> {
}
