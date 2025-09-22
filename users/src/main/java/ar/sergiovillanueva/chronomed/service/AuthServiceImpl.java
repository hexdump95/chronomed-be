package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.config.KeycloakAdminCliConfig;
import ar.sergiovillanueva.chronomed.config.KeycloakChronomedConfig;
import ar.sergiovillanueva.chronomed.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    private final RestTemplate restTemplate;
    private final KeycloakAdminCliConfig adminCliConfig;
    private final KeycloakChronomedConfig chronomedConfig;
    private final ObjectMapper objectMapper;
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String TOKEN_ATTRIBUTE_NAME = "access_token";
    private static final Short PAGE_SIZE = 10;

    public AuthServiceImpl(RestTemplate restTemplate, KeycloakAdminCliConfig adminCliConfig, KeycloakChronomedConfig chronomedConfig, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.adminCliConfig = adminCliConfig;
        this.chronomedConfig = chronomedConfig;
        this.objectMapper = objectMapper;
    }

    @Override
    public PageResponse<KeycloakUser> getUsersByName(String search, int page) {
        var url = chronomedConfig.getUrl() + "/users/count?search=" + search;
        var requestEntity = new HttpEntity<>(null, getKeycloakAdminCliJwtHeader());
        var countResponse = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Long.class);
        if (countResponse.getStatusCode() != HttpStatus.OK || countResponse.getBody() == null) {
            throw new KeycloakServiceException();
        }

        url = chronomedConfig.getUrl() + "/users?max=" + PAGE_SIZE + "&first=" + PAGE_SIZE * page + "&search=" + search;
        var response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<KeycloakUser>>() {
        });
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new KeycloakServiceException();
        }
        var users = response.getBody();
        return new PageResponse.Builder<KeycloakUser>()
                .items(users)
                .pageIndex(page)
                .pageSize(PAGE_SIZE)
                .totalItems(countResponse.getBody())
                .build();
    }

    @Override
    public KeycloakUser getUserById(UUID id) {
        var url = chronomedConfig.getUrl() + "/users/" + id;
        var requestEntity = new HttpEntity<>(null, getKeycloakAdminCliJwtHeader());
        var response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, KeycloakUser.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new KeycloakServiceException();
        }
        if (response.getBody() == null) {
            throw new NotFoundServiceException("user not found");
        }
        return response.getBody();
    }

    @Override
    public KeycloakUserDetail getUserDetailById(UUID id) {
        var keycloakUser = getUserById(id);
        var keycloakRoles = getRolesByUserId(id);
        var keycloakUserDetail = objectMapper.convertValue(keycloakUser, KeycloakUserDetail.class);
        keycloakUserDetail.setRoles(keycloakRoles);
        return keycloakUserDetail;
    }

    @Override
    public KeycloakUser createUser(KeycloakUserCreateRequest request) {
        var kcUser = objectMapper.convertValue(request, KeycloakUser.class);
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(true);
        var url = chronomedConfig.getUrl() + "/users";
        var requestEntity = new HttpEntity<>(kcUser, getKeycloakAdminCliJwtHeader());
        var countResponse = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Void.class);
        if (countResponse.getStatusCode() != HttpStatus.CREATED) {
            throw new KeycloakServiceException();
        }
        kcUser = getUserByEmail(request.getEmail());
        addRolesToUser(request.getRoles(), kcUser.getId());
        return kcUser;
    }

    @Override
    public KeycloakUser updateUser(UUID id, KeycloakUserUpdateRequest request) {
        var kcUser = objectMapper.convertValue(request, KeycloakUser.class);
        var url = chronomedConfig.getUrl() + "/users/" + id;
        var requestEntity = new HttpEntity<>(kcUser, getKeycloakAdminCliJwtHeader());
        var countResponse = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Void.class);
        if (countResponse.getStatusCode() != HttpStatus.NO_CONTENT) {
            throw new KeycloakServiceException();
        }
        var keycloakUserRoles = getRolesByUserId(id);
        List<KeycloakRole> rolesToAdd = new ArrayList<>();
        List<KeycloakRole> rolesToRemove = new ArrayList<>();
        request.getRoles().forEach(reqRole -> {
            if (!keycloakUserRoles.contains(reqRole))
                rolesToAdd.add(reqRole);
        });
        keycloakUserRoles.forEach(role -> {
            if (!request.getRoles().stream().map(KeycloakRole::getId).toList().contains(role.getId()))
                rolesToRemove.add(role);
        });
        if (!rolesToAdd.isEmpty())
            addRolesToUser(rolesToAdd, id);
        if (!rolesToRemove.isEmpty())
            removeRolesToUser(rolesToRemove, id);
        kcUser.setId(id);
        return kcUser;
    }

    @Override
    public void updateEmail(UUID id, String email) {
        var keycloakUserEmail = new KeycloakUpdateUserEmail();
        keycloakUserEmail.setEmail(email);
        var url = chronomedConfig.getUrl() + "/users/" + id;
        var requestEntity = new HttpEntity<>(keycloakUserEmail, getKeycloakAdminCliJwtHeader());
        var countResponse = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Void.class);
        if (countResponse.getStatusCode() != HttpStatus.NO_CONTENT) {
            throw new KeycloakServiceException();
        }
    }

    @Override
    public List<KeycloakRole> getRoles() {
        var url = chronomedConfig.getUrl() + "/clients/" + chronomedConfig.getClientUuid() + "/roles";
        var requestEntity = new HttpEntity<>(null, getKeycloakAdminCliJwtHeader());
        var response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<KeycloakRole>>() {
        });
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new KeycloakServiceException();
        }
        return response.getBody();
    }

    private HttpHeaders getKeycloakAdminCliJwtHeader() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", adminCliConfig.getGrantType());
        formData.add("client_id", adminCliConfig.getClientId());
        formData.add("username", adminCliConfig.getUsername());
        formData.add("password", adminCliConfig.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);

        try {
            var url = adminCliConfig.getUrl();
            ResponseEntity<HashMap<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {
            });
            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                throw new KeycloakServiceException();
            }
            var token = response.getBody().get(TOKEN_ATTRIBUTE_NAME).toString();
            var headerToken = new HttpHeaders();
            headerToken.add(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + token);
            return headerToken;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private KeycloakUser getUserByEmail(String email) {
        var url = chronomedConfig.getUrl() + "/users?email=" + email;
        var requestEntity = new HttpEntity<>(null, getKeycloakAdminCliJwtHeader());
        var response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<KeycloakUser>>() {
        });
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new KeycloakServiceException();
        }
        if (response.getBody() == null) {
            throw new NotFoundServiceException("user not found");
        }
        return response.getBody().getFirst();
    }

    private List<KeycloakRole> getRolesByUserId(UUID userId) {
        var url = chronomedConfig.getUrl() + "/users/" + userId.toString() + "/role-mappings/clients/" + chronomedConfig.getClientUuid() + "/composite";
        var requestEntity = new HttpEntity<>(null, getKeycloakAdminCliJwtHeader());
        var response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<KeycloakRole>>() {
        });
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new KeycloakServiceException();
        }
        if (response.getBody() == null) {
            throw new RuntimeException();
        }
        return response.getBody();
    }

    private void addRolesToUser(List<KeycloakRole> roles, UUID userId) {
        var url = chronomedConfig.getUrl() + "/users/" + userId + "/role-mappings/clients/" + chronomedConfig.getClientUuid();
        var requestEntity = new HttpEntity<>(roles, getKeycloakAdminCliJwtHeader());
        var response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Void.class);
        if (response.getStatusCode() != HttpStatus.NO_CONTENT) {
            throw new KeycloakServiceException();
        }
    }

    private void removeRolesToUser(List<KeycloakRole> roles, UUID userId) {
        var url = chronomedConfig.getUrl() + "/users/" + userId + "/role-mappings/clients/" + chronomedConfig.getClientUuid();
        var requestEntity = new HttpEntity<>(roles, getKeycloakAdminCliJwtHeader());
        var response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class);
        if (response.getStatusCode() != HttpStatus.NO_CONTENT) {
            throw new KeycloakServiceException();
        }
    }

}
