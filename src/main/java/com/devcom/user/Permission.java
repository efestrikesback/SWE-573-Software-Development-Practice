package com.devcom.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    MANAGER_READ("management:read"),
    MANAGER_UPDATE("management:update"),
    MANAGER_CREATE("management:create"),
    MANAGER_DELETE("management:delete"),
    USER_READ("user:delete"),
    USER_UPDATE("user:delete"),
    USER_CREATE("user:delete"),
    USER_DELETE("user:delete")
    ;

    @Getter
    private final String permission;
}
