package ar.sergiovillanueva.chronomed.specification;

import ar.sergiovillanueva.chronomed.entity.Specialty;
import ar.sergiovillanueva.chronomed.entity.Specialty_;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class SpecialtySpecification {
    public static Specification<Specialty> byDeletedAtNull() {
        return (root, query, criteriaBuilder) -> root.get(Specialty_.deletedAt).isNull();
    }

    public static Specification<Specialty> byNameLike(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.upper(root.get(Specialty_.name)),
                        "%" + name.toUpperCase() + "%"
                );
    }

    public static Specification<Specialty> byIds(List<Long> ids) {
        return (root, query, criteriaBuilder) -> root.get(Specialty_.id).in(ids);
    }
}
