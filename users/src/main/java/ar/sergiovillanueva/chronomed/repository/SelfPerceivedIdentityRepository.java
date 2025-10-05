package ar.sergiovillanueva.chronomed.repository;

import ar.sergiovillanueva.chronomed.entity.SelfPerceivedIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelfPerceivedIdentityRepository extends JpaRepository<SelfPerceivedIdentity, Long> {
}
