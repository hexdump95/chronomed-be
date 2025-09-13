package ar.sergiovillanueva.chronomed.mapper;

import ar.sergiovillanueva.chronomed.dto.*;
import ar.sergiovillanueva.chronomed.entity.Account;
import ar.sergiovillanueva.chronomed.entity.AccountFacility;
import ar.sergiovillanueva.chronomed.entity.AccountRole;
import ar.sergiovillanueva.chronomed.entity.AccountSpecialty;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class AccountMapper {
    public static KeycloakUserCreateRequest accountRequestToKeycloakUserCreateRequest(AccountRequest request, KeycloakUserCreateRequest keycloakUser) {
        keycloakUser.setUsername(request.getIdentityDocument());
        keycloakUser.setEmail(request.getEmail());
        keycloakUser.setFirstName(request.getFirstName());
        keycloakUser.setLastName(request.getLastName());
        return keycloakUser;
    }

    public static Account accountRequestToAccount(AccountRequest request, Account entity) {
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setIdentityDocument(request.getIdentityDocument());
        entity.setPhoneNumber(request.getPhoneNumber());
        entity.setFileNumber(request.getFileNumber());
        entity.setEmail(request.getEmail());
        var roles = request.getRoles().stream().map(kcRole -> {
            var role = new AccountRole();
            role.setRoleId(kcRole.getId());
            return role;
        }).toList();
        entity.setRoles(roles);
        var facilities = request.getFacilityIds().stream().map(facilityId -> {
            var facility = new AccountFacility();
            facility.setFacilityId(facilityId);
            return facility;
        }).toList();
        entity.setFacilities(facilities);
        var specialties = request.getSpecialtyIds().stream().map(specialtyId -> {
            var specialty = new AccountSpecialty();
            specialty.setSpecialtyId(specialtyId);
            return specialty;
        }).toList();
        entity.setSpecialties(specialties);
        return entity;
    }

    public static void accountUpdateRequestToAccount(AccountUpdateRequest request, Account entity) {
        entity.setPhoneNumber(request.getPhoneNumber());
        entity.setFileNumber(request.getFileNumber());

        entity.getRoles().clear();
        var roles = request.getRoles().stream().map(kcRole -> {
            var role = new AccountRole();
            role.setRoleId(kcRole.getId());
            return role;
        }).collect(Collectors.toCollection(ArrayList::new));
        entity.getRoles().addAll(roles);

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
        response.setId(entity.getId());
        response.setFirstName(entity.getFirstName());
        response.setLastName(entity.getLastName());
        response.setIdentityDocument(entity.getIdentityDocument());
        response.setPhoneNumber(entity.getPhoneNumber());
        response.setFileNumber(entity.getFileNumber());
        response.setEmail(entity.getEmail());
        var roleIds = entity.getRoles().stream().map(AccountRole::getRoleId).toList();
        response.setRoleIds(roleIds);
        var facilityIds = entity.getFacilities().stream().map(AccountFacility::getFacilityId).toList();
        response.setFacilityIds(facilityIds);
        var specialtyIds = entity.getSpecialties().stream().map(AccountSpecialty::getSpecialtyId).toList();
        response.setSpecialtyIds(specialtyIds);
        return response;
    }

}
