package ar.sergiovillanueva.chronomed.specification;

import ar.sergiovillanueva.chronomed.entity.Room;
import ar.sergiovillanueva.chronomed.entity.Room_;
import org.springframework.data.jpa.domain.Specification;

public class RoomSpecification {
    public static Specification<Room> byDeletedAtNull() {
        return (root, query, criteriaBuilder) -> root.get(Room_.deletedAt).isNull();
    }
}
