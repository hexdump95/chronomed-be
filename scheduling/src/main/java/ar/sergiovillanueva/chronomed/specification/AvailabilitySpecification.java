package ar.sergiovillanueva.chronomed.specification;

import ar.sergiovillanueva.chronomed.entity.Availability;
import ar.sergiovillanueva.chronomed.entity.Availability_;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class AvailabilitySpecification {
    public static Specification<Availability> byUserId(UUID userId) {
        return (root, criteriaQuery, criteriaBuilder) ->
            criteriaBuilder.equal(root.get(Availability_.accountId), userId);
    }
}
