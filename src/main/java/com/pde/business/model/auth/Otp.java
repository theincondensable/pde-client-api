package com.pde.business.model.auth;

import com.pde.business.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * @author abbas
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Otp extends BaseEntity {

    private Integer otpCode;
    private Integer otpCounter;
    private Instant activeUntil;
    private Instant inactiveUntil;

    @OneToOne
    @JoinColumn(name = "FK_USER")
    private User user;

    public void increaseOtpCounter() {
        this.otpCounter += 1;
    }

}
