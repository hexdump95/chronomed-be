package ar.sergiovillanueva.chronomed.specification;

import ar.sergiovillanueva.chronomed.entity.Specialty;
import ar.sergiovillanueva.chronomed.entity.SpecialtyPrice;
import ar.sergiovillanueva.chronomed.entity.SpecialtyPrice_;
import ar.sergiovillanueva.chronomed.entity.Specialty_;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public class SpecialtyPriceSpecification {
    public static Specification<SpecialtyPrice> byId(Long specialtyId) {
        return (root, query, criteriaBuilder) -> {
            Join<SpecialtyPrice, Specialty> join = root.join(SpecialtyPrice_.specialty);
            return criteriaBuilder.equal(join.get(Specialty_.id), specialtyId);
        };
    }

    public static Specification<SpecialtyPrice> byDateBetween(Instant validFrom, Instant validTo) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.and(
                                criteriaBuilder.lessThanOrEqualTo(root.get(SpecialtyPrice_.validFrom), validFrom),
                                criteriaBuilder.greaterThanOrEqualTo(root.get(SpecialtyPrice_.validTo), validFrom)
                        ),
                        criteriaBuilder.and(
                                criteriaBuilder.lessThanOrEqualTo(root.get(SpecialtyPrice_.validFrom), validTo),
                                criteriaBuilder.greaterThanOrEqualTo(root.get(SpecialtyPrice_.validTo), validTo)
                        )
                );
    }

}
