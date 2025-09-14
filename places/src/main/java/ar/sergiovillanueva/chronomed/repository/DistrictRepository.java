package ar.sergiovillanueva.chronomed.repository;

import ar.sergiovillanueva.chronomed.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    List<District> findAllByProvinceId(Long provinceId);
}
