package com.it.doubledi.cinemamanager.infrastructure.support.enums;

import com.it.doubledi.cinemamanager.common.model.enums.Scope;

import java.util.List;

public enum ResourceCategory {
    USER_MANAGER("USER", "USER_MANAGEMENT_TITLE", List.of(Scope.CREATE, Scope.UPDATE, Scope.VIEW, Scope.DELETE)),
    ZOOM_MANAGER("ZOOM", "ZOOM_MANAGEMENT_TITLE", List.of(Scope.CREATE, Scope.UPDATE, Scope.VIEW, Scope.DELETE)),
    FILM_MANAGER("FILM", "FILM_MANAGEMENT_TITLE", List.of(Scope.CREATE, Scope.UPDATE, Scope.VIEW, Scope.DELETE)),
    FILM_TYPE_MANAGER("FILM_TYPE", "FILM_TYPE_MANAGEMENT_TITLE", List.of(Scope.CREATE, Scope.UPDATE, Scope.VIEW, Scope.DELETE)),
    ROLE_MANAGER("ROLE", "ROLE_MANAGEMENT_TITLE", List.of(Scope.CREATE, Scope.UPDATE, Scope.VIEW, Scope.DELETE));
    String resourceCode;
    String resourceName;
    List<Scope> scopes;

    ResourceCategory(String resourceCode, String resourceName, List<Scope> scopes) {
        this.resourceCode = resourceCode;
        this.resourceName = resourceName;
        this.scopes = scopes;
    }


    public String getResourceCode() {
        return resourceCode;
    }

    public String getResourceName() {
        return resourceName;
    }

    public List<Scope> getScopes() {
        return scopes;
    }
}
    
