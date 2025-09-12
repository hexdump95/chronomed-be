package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.config.KeycloakChronomedConfig;
import ar.sergiovillanueva.chronomed.dto.KeycloakRole;
import ar.sergiovillanueva.chronomed.dto.KeycloakUser;
import ar.sergiovillanueva.chronomed.dto.PageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import ar.sergiovillanueva.chronomed.config.KeycloakAdminCliConfig;

import java.util.HashMap;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService<KeycloakUser, KeycloakRole> {
    private final RestTemplate restTemplate;
    private final KeycloakAdminCliConfig adminCliConfig;
    private final KeycloakChronomedConfig chronomedConfig;
    @Value("${httpclient.keycloak.url}")
    private String keycloakUrl;
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String TOKEN_ATTRIBUTE_NAME = "access_token";
    private static final Short PAGE_SIZE = 10;

    public AuthServiceImpl(RestTemplate restTemplate, KeycloakAdminCliConfig adminCliConfig, KeycloakChronomedConfig chronomedConfig) {
        this.restTemplate = restTemplate;
        this.adminCliConfig = adminCliConfig;
        this.chronomedConfig = chronomedConfig;
    }

    public HttpHeaders getKeycloakAdminCliJwtHeader() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", adminCliConfig.getGrantType());
        formData.add("client_id", adminCliConfig.getClientId());
        formData.add("username", adminCliConfig.getUsername());
        formData.add("password", adminCliConfig.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);

        try {
            var url = keycloakUrl + "/realms/master/protocol/openid-connect/token";
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

    @Override
    public PageResponse<KeycloakUser> getUsers(int page) {
        var url = keycloakUrl + "/admin/realms/" + chronomedConfig.getClientId() + "/users/count";
        var requestEntity = new HttpEntity<>(null, getKeycloakAdminCliJwtHeader());
        var countResponse = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Long.class);
        if (countResponse.getStatusCode() != HttpStatus.OK || countResponse.getBody() == null) {
            throw new KeycloakServiceException();
        }

        url = keycloakUrl + "/admin/realms/" + chronomedConfig.getClientId() + "/users?max=" + PAGE_SIZE + "&first=" + PAGE_SIZE * page;
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
    public List<KeycloakRole> getRoles() {
        var url = keycloakUrl + "/admin/realms/" + chronomedConfig.getClientId() + "/clients/" + chronomedConfig.getClientUuid() + "/roles";
        var requestEntity = new HttpEntity<>(null, getKeycloakAdminCliJwtHeader());
        var response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<KeycloakRole>>() {
        });
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new KeycloakServiceException();
        }
        return response.getBody();
    }

}
