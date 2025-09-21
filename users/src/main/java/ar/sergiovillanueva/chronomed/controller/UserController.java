package ar.sergiovillanueva.chronomed.controller;

import ar.sergiovillanueva.chronomed.dto.*;
import ar.sergiovillanueva.chronomed.service.AccountService;
import ar.sergiovillanueva.chronomed.service.AuthService;
import ar.sergiovillanueva.chronomed.service.NotFoundServiceException;
import ar.sergiovillanueva.chronomed.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final AuthService authService;
    private final AccountService accountService;
    private final RoleService roleService;

    public UserController(AuthService authService, AccountService accountService, RoleService roleService) {
        this.authService = authService;
        this.accountService = accountService;
        this.roleService = roleService;
    }

    @GetMapping()
    public ResponseEntity<PageResponse<KeycloakUser>> getUsers(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page
    ) {
        log.info("GET request to getUsers");
        try {
            return ResponseEntity.ok(authService.getUsersByName(search, page));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<KeycloakUserDetail> getUserDetail(@PathVariable UUID id) {
        log.info("GET request to getUser {}", id);
        try {
            return ResponseEntity.ok(authService.getUserDetailById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<KeycloakUser> createUser(@RequestBody KeycloakUserCreateRequest request) {
        log.info("POST request to createUser {}", request);
        try {
            return ResponseEntity.ok(authService.createUser(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<KeycloakUser> updateUser(@PathVariable UUID id, @RequestBody KeycloakUserUpdateRequest request) {
        log.info("POST request to updateUser id {} {}", id, request);
        try {
            return ResponseEntity.ok(authService.updateUser(id, request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{userId}/account")
    public ResponseEntity<AccountResponse> getAccountByUserId(@PathVariable UUID userId) {
        log.info("GET request to getAccount with id: {}", userId);
        try {
            return ResponseEntity.ok(accountService.findByUserId(userId));
        } catch (NotFoundServiceException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{userId}/account")
    public ResponseEntity<AccountResponse> updateAccountByUserId(@PathVariable UUID userId, @RequestBody AccountUpdateRequest request) {
        log.info("PUT request to updateAccount");
        try {
            return ResponseEntity.ok(accountService.updateAccount(userId, request));
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
