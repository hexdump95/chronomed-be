package ar.sergiovillanueva.chronomed.specification;

import ar.sergiovillanueva.chronomed.entity.Comorbidity;
import ar.sergiovillanueva.chronomed.entity.Comorbidity_;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ComorbiditySpecification {
    public static Specification<Comorbidity> byDeletedAtNull() {
        return (root, query, criteriaBuilder) -> root.get(Comorbidity_.deletedAt).isNull();
    }

    public static Specification<Comorbidity> byNameLike(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.upper(root.get(Comorbidity_.name)),
                        "%" + name.toUpperCase() + "%"
                );
    }

    public static Specification<Comorbidity> byIds(List<Long> ids) {
        return (root, query, criteriaBuilder) -> root.get(Comorbidity_.id).in(ids);
    }
}
