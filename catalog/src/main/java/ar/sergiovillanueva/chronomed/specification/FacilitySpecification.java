package ar.sergiovillanueva.chronomed.specification;

import ar.sergiovillanueva.chronomed.entity.Facility;
import ar.sergiovillanueva.chronomed.entity.Facility_;
import org.springframework.data.jpa.domain.Specification;

public class FacilitySpecification {
    public static Specification<Facility> byDeletedAtNull() {
        return (root, query, criteriaBuilder) -> root.get(Facility_.deletedAt).isNull();
    }

    public static Specification<Facility> byNameLike(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.upper(root.get(Facility_.name)),
                        "%" + name.toUpperCase() + "%"
                );
    }
}
