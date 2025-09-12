package ar.sergiovillanueva.chronomed.controller;

import ar.sergiovillanueva.chronomed.dto.KeycloakRole;
import ar.sergiovillanueva.chronomed.dto.KeycloakUser;
import ar.sergiovillanueva.chronomed.dto.PageResponse;
import ar.sergiovillanueva.chronomed.service.AccountService;
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
    private final AccountService accountService;
    private final RoleService roleService;

    public AccountController(AccountService accountService, RoleService roleService) {
        this.accountService = accountService;
        this.roleService = roleService;
    }

    @GetMapping()
    public ResponseEntity<PageResponse<KeycloakUser>> getAccounts(@RequestParam int page) {
        log.info("GET request to getAccounts");
        try {
            return ResponseEntity.ok(accountService.findAll(page));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
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
