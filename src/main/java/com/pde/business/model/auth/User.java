package com.pde.business.model.auth;

import com.pde.business.model.BaseEntity;
import com.pde.business.model.RoleEnum;
import com.pde.web.dto.auth.register.BaseUserRegisterRequestDTO;
import com.pde.web.dto.auth.register.CompanyUserRegisterRequestDTO;
import com.pde.web.dto.auth.register.PersonUserRegisterRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * @author abbas
 */
@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(callSuper=true)
public class User extends BaseEntity {

    @Column(nullable = false)
    private String farsiFirstName;
    @Column(nullable = false)
    private String farsiLastName;

    @Column(nullable = false)
    private String englishFirstName;
    @Column(nullable = false)
    private String englishLastName;

    @Column(unique = true, nullable = false)
    private String emailAddressMain;

    @Column(unique = true)
    private String emailAddressSecondary;

    @Column(unique = true, nullable = false)
    private String mobilePhoneNumber;

    @Column(nullable = false)
    private String password;

    private String nationalId;

    @Column(nullable = false)
    private String country;

    @Setter
    private Boolean isActive;

    private Boolean isCompany;

    @Temporal(TemporalType.DATE)
    private Date birthdate;

    @Setter
    private Boolean isFormal;
    @Setter
    private Long marketerId;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    /**
     * <p>
     * A Person IS NOT A COMPANY.<br>
     * A Person needs National ID.
     * A Person needs its Birthdate for Bashgah-Moshtariyan
     * </p>
     *
     * @param req      Person Registration Request
     * @param password Hashed Password in UserService
     * @return the User
     */
    public static User createPerson(PersonUserRegisterRequestDTO req, String password) {
        User user = new User();
        user.setNationalId(req.getNationalId());
        user.setBirthdate(req.getBirthDate());
        mutateSharedFields(user, req.getUser(), password);
        user.setIsCompany(false);

        user.setRole(RoleEnum.FORMAL_USER);

        if (req.getNationalId().equals("1741873460"))
            if (user.getEmailAddressMain().equals("admin@gmail.com"))
                user.setRole(RoleEnum.ADMIN_DEVELOPER);

        return user;
    }

    /**
     * <p>
     * A Company IS A COMPANY.<br>
     * A Company DOES NOT need National ID, it needs a NationalRegistrationCode, instead.
     * </p>
     *
     * @param req      Company Registration Request
     * @param password Hashed Password in UserService
     * @return the User
     */
    public static User createCompany(CompanyUserRegisterRequestDTO req, String password) {
        User user = new User();
        mutateSharedFields(user, req.getCompanyContactPerson(), password);
        user.setBirthdate(null);
        user.setNationalId(null);
        user.setIsCompany(true);

        return user;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    private static void mutateSharedFields(User user, BaseUserRegisterRequestDTO req, String password) {
        user.setFarsiFirstName(req.getFarsiFirstName());
        user.setFarsiLastName(req.getFarsiLastName());
        user.setEnglishFirstName(req.getEnglishFirstName());
        user.setEnglishLastName(req.getEnglishLastName());
        user.setEmailAddressMain(req.getMainEmailAddress());
        user.setEmailAddressSecondary(req.getSecondaryEmailAddress());
        user.setMobilePhoneNumber(req.getMobilePhoneNumber());
        user.setCountry(req.getCountry());
        user.setPassword(password);
        user.setIsActive(false);
        user.setIsFormal(true);
        user.setMarketerId(null);
    }

}

//    @JoinColumn(name = "FK_TOKEN")
//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Token token;

//    @Setter
//    @JoinColumn(name = "FK_OTP")
//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Otp otp;

//    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "USER_ROLE",
//            joinColumns = @JoinColumn(name = "FK_USER"),
//            inverseJoinColumns = @JoinColumn(name = "FK_ROLE")
//    )