package ar.sergiovillanueva.chronomed.controller;

import ar.sergiovillanueva.chronomed.config.AuthRole;
import ar.sergiovillanueva.chronomed.dto.ProfileResponse;
import ar.sergiovillanueva.chronomed.security.RolesRequired;
import ar.sergiovillanueva.chronomed.service.StaffProfileService;
import ar.sergiovillanueva.chronomed.utils.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile")
public class StaffProfileController {
    private final StaffProfileService profileService;

    public StaffProfileController(StaffProfileService profileService) {
        this.profileService = profileService;
    }

    @RolesRequired({AuthRole.ADMIN, AuthRole.DOCTOR, AuthRole.RECEPTIONIST})
    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile() {
        return ResponseEntity.ok(profileService.getProfile(JwtUtils.extractUserId()));
    }
}
