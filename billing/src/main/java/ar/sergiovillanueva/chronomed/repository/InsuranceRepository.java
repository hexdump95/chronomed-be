package ar.sergiovillanueva.chronomed.repository;

import ar.sergiovillanueva.chronomed.entity.Insurance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
    Page<Insurance> findAll(Specification<Insurance> specification, Pageable pageable);

    List<Insurance> findAll(Specification<Insurance> specification, Sort sort);

    List<Insurance> findAll(Specification<Insurance> specification);

    long count(Specification<Insurance> specification);
}
