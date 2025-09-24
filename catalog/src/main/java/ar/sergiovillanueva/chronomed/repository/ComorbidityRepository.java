package ar.sergiovillanueva.chronomed.repository;

import ar.sergiovillanueva.chronomed.entity.Comorbidity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComorbidityRepository extends JpaRepository<Comorbidity, Long> {
    Page<Comorbidity> findAll(Specification<Comorbidity> specification, Pageable pageable);

    List<Comorbidity> findAll(Specification<Comorbidity> specification, Sort sort);

    long count(Specification<Comorbidity> specification);
}
