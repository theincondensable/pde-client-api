package com.pde.business.model.auth;

import com.pde.business.model.BaseEntity;
import com.pde.business.service.auth.TokensEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * @author abbas
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Token extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String jwt;

    private Instant activeTime;

    @OneToOne
    @JoinColumn(name = "FK_USER")
    private User user;

    public void setActiveTime(Instant now, Boolean rememberMe) {
        if (rememberMe)
            this.activeTime = Instant.from(now.plus(TokensEnum.JWT_TOKEN_WITH_REMEMBER_ME_EXPIRES_IN_MINUTES, ChronoUnit.MINUTES));
        else
            this.activeTime = Instant.from(now.plus(TokensEnum.JWT_TOKEN_EXPIRES_IN_MINUTES, ChronoUnit.MINUTES));
    }

}
