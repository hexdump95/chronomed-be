package ar.sergiovillanueva.chronomed.repository;

import ar.sergiovillanueva.chronomed.entity.SpecialtyPrice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtyPriceRepository extends JpaRepository<SpecialtyPrice, Long> {
    long count(Specification<SpecialtyPrice> specification);
}
