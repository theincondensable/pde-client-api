package com.pde.business.repository.auth;

import com.pde.business.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author abbas
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional(readOnly = true)
    Boolean existsUserByEmailAddressMain(@Param("emailAddressMain") String emailAddressMain);

    @Transactional(readOnly = true)
    @Query("select u from User u where u.emailAddressMain = :email OR u.emailAddressSecondary = :email")
    Optional<User> findUserByAnyOfBothEmailAddresses(@Param("email") String email);

    @Transactional(readOnly = true)
    Optional<User> findUserByMobilePhoneNumber(String phoneNumber);

    @Query(value = "select COUNT(u.C_ID) from T_USER u where (u.C_EMAIL_ADDRESS_MAIN = :mailAddress OR u.C_EMAIL_ADDRESS_SECONDARY = :mailAddress)", nativeQuery = true)
    Long countsUsersByBothMailAddresses(@Param("mailAddress") String mailAddress);

    Boolean existsUserByNationalId(String nationalId);
}
