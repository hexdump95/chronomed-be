package ar.sergiovillanueva.chronomed.repository;

import ar.sergiovillanueva.chronomed.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {
}
