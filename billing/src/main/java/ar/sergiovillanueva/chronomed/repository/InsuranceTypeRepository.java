package ar.sergiovillanueva.chronomed.repository;

import ar.sergiovillanueva.chronomed.entity.InsuranceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceTypeRepository extends JpaRepository<InsuranceType, Long> {
}
