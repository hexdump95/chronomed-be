package ar.sergiovillanueva.chronomed.mapper;

import ar.sergiovillanueva.chronomed.dto.AccountResponse;
import ar.sergiovillanueva.chronomed.dto.AccountUpdateRequest;
import ar.sergiovillanueva.chronomed.entity.Account;
import ar.sergiovillanueva.chronomed.entity.AccountFacility;
import ar.sergiovillanueva.chronomed.entity.AccountSpecialty;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class AccountMapper {

    public static void accountUpdateRequestToAccount(AccountUpdateRequest request, Account entity) {
        entity.setPhoneNumber(request.getPhoneNumber());
        entity.setFileNumber(request.getFileNumber());

        entity.getFacilities().clear();
        var facilities = request.getFacilityIds().stream().map(facilityId -> {
            var facility = new AccountFacility();
            facility.setFacilityId(facilityId);
            return facility;
        }).collect(Collectors.toCollection(ArrayList::new));
        entity.getFacilities().addAll(facilities);

        entity.getSpecialties().clear();
        var specialties = request.getSpecialtyIds().stream().map(specialtyId -> {
            var specialty = new AccountSpecialty();
            specialty.setSpecialtyId(specialtyId);
            return specialty;
        }).collect(Collectors.toCollection(ArrayList::new));
        entity.getSpecialties().addAll(specialties);
    }

    public static AccountResponse accountToAccountResponse(Account entity, AccountResponse response) {
        response.setId(entity.getUserId());
        response.setPhoneNumber(entity.getPhoneNumber());
        response.setFileNumber(entity.getFileNumber());
        var facilityIds = entity.getFacilities().stream().map(AccountFacility::getFacilityId).toList();
        response.setFacilityIds(facilityIds);
        var specialtyIds = entity.getSpecialties().stream().map(AccountSpecialty::getSpecialtyId).toList();
        response.setSpecialtyIds(specialtyIds);
        return response;
    }

}
