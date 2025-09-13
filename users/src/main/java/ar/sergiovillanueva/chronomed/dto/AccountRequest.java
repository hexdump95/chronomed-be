package ar.sergiovillanueva.chronomed.dto;

import java.util.ArrayList;
import java.util.List;

public class AccountRequest {
    private String userFirstName;
    private String userLastName;
    private String userUsername;
    private String accountPhoneNumber;
    private String accountFileNumber;
    private String userEmail;
    private List<KeycloakRole> userRoles = new ArrayList<>();
    private List<Long> accountFacilityIds = new ArrayList<>();
    private List<Long> accountSpecialtyIds = new ArrayList<>();

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getAccountPhoneNumber() {
        return accountPhoneNumber;
    }

    public void setAccountPhoneNumber(String accountPhoneNumber) {
        this.accountPhoneNumber = accountPhoneNumber;
    }

    public String getAccountFileNumber() {
        return accountFileNumber;
    }

    public void setAccountFileNumber(String accountFileNumber) {
        this.accountFileNumber = accountFileNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<KeycloakRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<KeycloakRole> userRoles) {
        this.userRoles = userRoles;
    }

    public List<Long> getAccountFacilityIds() {
        return accountFacilityIds;
    }

    public void setAccountFacilityIds(List<Long> accountFacilityIds) {
        this.accountFacilityIds = accountFacilityIds;
    }

    public List<Long> getAccountSpecialtyIds() {
        return accountSpecialtyIds;
    }

    public void setAccountSpecialtyIds(List<Long> accountSpecialtyIds) {
        this.accountSpecialtyIds = accountSpecialtyIds;
    }
}
