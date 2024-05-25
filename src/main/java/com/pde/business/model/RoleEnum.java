package com.pde.business.model;

import lombok.Getter;

/**
 * @author abbas
 */
@Getter
public enum RoleEnum {

    ADMIN_DEVELOPER("ADMIN_DEVELOPER", "This is myself ;)"),
    FORMAL_USER("FORMAL_USER", "Formal User who can see all their orders."),
    INFORMAL_USER("INFORMAL_USER", "Informal User who can't see any of their orders.");

    private final String name;
    private final String description;

    RoleEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
