package ar.sergiovillanueva.chronomed.controller;

import ar.sergiovillanueva.chronomed.dto.KeycloakRole;
import ar.sergiovillanueva.chronomed.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final Logger log = LoggerFactory.getLogger(AccountController.class);
    private final RoleService roleService;

    public AccountController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public ResponseEntity<List<KeycloakRole>> getRoles() {
        log.debug("GET request to getRoles");
        try {
            return ResponseEntity.ok(roleService.getRoles());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
