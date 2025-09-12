package ar.sergiovillanueva.chronomed.service;

import ar.sergiovillanueva.chronomed.dto.PageResponse;

import java.util.List;

public interface AuthService<T1, T2> {
    PageResponse<T1> getUsers(int page);
    List<T2> getRoles();
}
