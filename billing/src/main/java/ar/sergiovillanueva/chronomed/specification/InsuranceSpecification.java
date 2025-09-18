package ar.sergiovillanueva.chronomed.specification;

import ar.sergiovillanueva.chronomed.entity.Insurance;
import ar.sergiovillanueva.chronomed.entity.InsuranceType;
import ar.sergiovillanueva.chronomed.entity.InsuranceType_;
import ar.sergiovillanueva.chronomed.entity.Insurance_;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class InsuranceSpecification {
    public static Specification<Insurance> byDeletedAtNull() {
        return (root, query, criteriaBuilder) -> root.get(Insurance_.deletedAt).isNull();
    }

    public static Specification<Insurance> byNameLike(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.upper(root.get(Insurance_.name)),
                        "%" + name.toUpperCase() + "%"
                );
    }

    public static Specification<Insurance> byInsuranceTypeId(Long insuranceTypeId) {
        return (root, query, criteriaBuilder) -> {
            Join<Insurance, InsuranceType> insuranceTypeJoin = root.join(Insurance_.insuranceType);
            return criteriaBuilder.equal(insuranceTypeJoin.get(InsuranceType_.id), insuranceTypeId);
        };
    }

    public static Specification<Insurance> fetchInsuranceType() {
        return (root, query, criteriaBuilder) -> {
            root.fetch(Insurance_.insuranceType, JoinType.INNER);
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Insurance> byIds(List<Long> ids) {
        return (root, query, criteriaBuilder) -> root.get(Insurance_.id).in(ids);
    }

}
