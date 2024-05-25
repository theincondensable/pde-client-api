package com.pde.business.model.information;

import com.pde.business.model.BaseEntity;
import com.pde.business.model.auth.User;
import jakarta.persistence.*;
import lombok.*;

/**
 * @author abbas
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket extends BaseEntity {

    private String code;
    private String title;
    private String comment;

    @Enumerated(EnumType.STRING)
    private TicketStatesEnum state;


    @OneToOne
    @JoinColumn(name = "FK_USER")
    private User user;

}
