package ar.sergiovillanueva.chronomed.repository;

import ar.sergiovillanueva.chronomed.entity.Comorbidity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComorbidityRepository extends JpaRepository<Comorbidity, Long> {
    Page<Comorbidity> findAll(Specification<Comorbidity> specification, Pageable pageable);
}
