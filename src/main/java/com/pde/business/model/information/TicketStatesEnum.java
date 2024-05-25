package com.pde.business.model.information;

import lombok.Getter;

/**
 * @author abbas
 */
@Getter
public enum TicketStatesEnum {
    ISSUED("The Ticket is issued."),
    BEING_PROCEED("The Ticket is being processed."),
    FINISHED("The Ticket consideration is finished.");

    private final String title;

    TicketStatesEnum(String title) {
        this.title = title;
    }

}
