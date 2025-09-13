package ar.sergiovillanueva.chronomed.controller;

import ar.sergiovillanueva.chronomed.dto.*;
import ar.sergiovillanueva.chronomed.service.AccountService;
import ar.sergiovillanueva.chronomed.service.NotFoundServiceException;
import ar.sergiovillanueva.chronomed.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<PageResponse<KeycloakUser>> getAccounts(@RequestParam(defaultValue = "0") int page) {
        log.info("GET request to getAccounts");
        try {
            return ResponseEntity.ok(accountService.findAll(page));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable UUID id) {
        log.info("GET request to getAccount with id: {}", id);
        try {
            return ResponseEntity.ok(accountService.findById(id));
        } catch (NotFoundServiceException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest request) {
        log.info("POST request to createAccount");
        try {
            return ResponseEntity.ok(accountService.createAccount(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable UUID id, @RequestBody AccountUpdateRequest request) {
        log.info("PUT request to updateAccount");
        try {
            return ResponseEntity.ok(accountService.updateAccount(id, request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/roles/all")
    public ResponseEntity<List<KeycloakRole>> getAllRoles() {
        log.debug("GET request to getAllRoles");
        try {
            return ResponseEntity.ok(roleService.getRoles());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
