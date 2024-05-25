package com.pde.business.model.information;

/**
 * @author abbas
 */
public enum TicketEnum {

    OPERATION("some operational title", "this is a sample title");

    private final String title;
    private final String description;

    TicketEnum(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
