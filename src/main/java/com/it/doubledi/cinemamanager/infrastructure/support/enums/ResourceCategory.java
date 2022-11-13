package com.it.doubledi.cinemamanager.infrastructure.support.enums;

import com.it.doubledi.cinemamanager._common.model.enums.Scope;

import java.util.List;

public enum ResourceCategory {
    USER_MANAGER("USER", "USER_MANAGEMENT_TITLE", List.of(Scope.CREATE, Scope.UPDATE, Scope.VIEW, Scope.DELETE), 1),
    ZOOM_MANAGER("ZOOM", "ZOOM_MANAGEMENT_TITLE", List.of(Scope.CREATE, Scope.UPDATE, Scope.VIEW, Scope.DELETE), 2),
    FILM_MANAGER("FILM", "FILM_MANAGEMENT_TITLE", List.of(Scope.CREATE, Scope.UPDATE, Scope.VIEW, Scope.DELETE), 3),
    FILM_TYPE_MANAGER("FILM_TYPE", "FILM_TYPE_MANAGEMENT_TITLE", List.of(Scope.CREATE, Scope.UPDATE, Scope.VIEW, Scope.DELETE), 4),
    ROLE_MANAGER("ROLE", "ROLE_MANAGEMENT_TITLE", List.of(Scope.CREATE, Scope.UPDATE, Scope.VIEW, Scope.DELETE), 5);
    String resourceCode;
    String resourceName;
    List<Scope> scopes;
    int priority;

    ResourceCategory(String resourceCode, String resourceName, List<Scope> scopes, int priority) {
        this.resourceCode = resourceCode;
        this.resourceName = resourceName;
        this.scopes = scopes;
        this.priority = priority;
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

    public int getPriority(){ return this.priority;}
}
    
