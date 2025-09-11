package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.ChronomedApplication;
import ar.sergiovillanueva.chronomed.dto.SpecialtyDetailResponse;
import ar.sergiovillanueva.chronomed.entity.Specialty;
import ar.sergiovillanueva.chronomed.entity.SpecialtyPrice;
import ar.sergiovillanueva.chronomed.repository.SpecialtyRepository;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ChronomedApplication.class)
@Transactional
class SpecialtyServiceIntegrationTest {

    @Autowired
    private SpecialtyService specialtyService;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Test
    void findOne_shouldHaveSpecialtyPricesOrdererByValidToAsc() {
        var specialty = new Specialty();
        specialty.setName("Cardiology");

        List<SpecialtyPrice> specialtyPrices = List.of(
                createSpecialtyPrice(Instant.parse("2025-01-01T00:00:00Z"), Instant.parse("2025-01-31T00:00:00Z"), 100.0),
                createSpecialtyPrice(Instant.parse("2025-03-01T00:00:00Z"), Instant.parse("2025-03-31T00:00:00Z"), 300.0),
                createSpecialtyPrice(Instant.parse("2025-02-01T00:00:00Z"), Instant.parse("2025-02-28T00:00:00Z"), 200.0)
        );
        specialty.setSpecialtyPrices(specialtyPrices);

        specialtyRepository.save(specialty);

        SpecialtyDetailResponse result = specialtyService.findOne(1L);

        assertThat(result.getSpecialtyPrices().get(0).getValidFrom()).isEqualTo(Instant.parse("2025-03-01T00:00:00Z"));
        assertThat(result.getSpecialtyPrices().get(1).getValidFrom()).isEqualTo(Instant.parse("2025-02-01T00:00:00Z"));
        assertThat(result.getSpecialtyPrices().get(2).getValidFrom()).isEqualTo(Instant.parse("2025-01-01T00:00:00Z"));
    }

    private SpecialtyPrice createSpecialtyPrice(Instant validFrom, Instant validTo, Double price) {
        var specialtyPrice = new SpecialtyPrice();
        specialtyPrice.setValidFrom(validFrom);
        specialtyPrice.setValidTo(validTo);
        specialtyPrice.setPrice(price);
        return specialtyPrice;
    }

}
