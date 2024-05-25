package com.pde.business.repository.auth;

import com.pde.business.model.auth.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author abbas
 */
@Repository
public interface OTPRepository extends JpaRepository<Otp, Long> {

    @Query("select o from Otp o where o.user.id = :userId")
    Optional<Otp> findOtpByUserId(@Param("userId") Long userId);

    @Query("select o from Otp o where o.user.mobilePhoneNumber = :mobilePhoneNumber")
    Optional<Otp> findByUserPhoneNumber(@Param("mobilePhoneNumber") String mobilePhoneNumber);

}
