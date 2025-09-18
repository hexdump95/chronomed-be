package ar.sergiovillanueva.chronomed.repository;

import ar.sergiovillanueva.chronomed.entity.Availability;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findAll(Specification<Availability> specification);
}
