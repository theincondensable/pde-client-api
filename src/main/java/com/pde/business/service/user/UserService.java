package com.pde.business.service.user;

import com.pde.business.exceptions.auth.CurrentPasswordWrongException;
import com.pde.business.exceptions.auth.NewPasswordSameAsOldPasswordException;
import com.pde.business.exceptions.auth.PasswordAndConfirmPasswordMismatchException;
import com.pde.business.exceptions.auth.user.*;
import com.pde.business.model.auth.User;
import com.pde.business.repository.auth.UserRepository;
import com.pde.business.service.LoggedInUserExtractor;
import com.pde.business.service.customer.CompanyService;
import com.pde.web.dto.auth.ChangePasswordRequestDTO;
import com.pde.web.dto.auth.register.CompanyUserRegisterRequestDTO;
import com.pde.web.dto.auth.register.PersonUserRegisterRequestDTO;
import lombok.AllArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author abbas
 */
@Service
@AllArgsConstructor
public class UserService {

    private final String className = this.getClass().getSimpleName();

    private final UserRepository userRepository;
    private final CompanyService companyService;
    private final PasswordEncoder passwordEncoder;
    private final LoggedInUserExtractor userExtractor;
    private final NationalIdValidator nationalIdValidator;
    private final UserInformationValidator userInformationValidator;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findUserByMobilePhoneNumber(phoneNumber).orElseThrow(() -> new UserNotFoundWithPhoneNumberException(className, phoneNumber));
    }

    public User findUserByEmailAddress(String emailAddress) {
        return userRepository.findUserByAnyOfBothEmailAddresses(emailAddress).orElseThrow(() -> new UserNotFoundWithEmailException(className, emailAddress));
    }

    /**
     * <p>It can only be used in Person Registration flow.</p>
     *
     * <p>
     * Validation for Main Email Address is mandatory.<br>
     * Validation for Secondary Email Address if it is given, is mandatory.
     * Validation for National ID is only executed when the User is IRANIAN.
     * </p>
     *
     * @param req the incoming request to Create a Person type of User
     * @return the created Person
     */
    @Transactional
    public User registerPersonAccount(PersonUserRegisterRequestDTO req) {
        validateEmailAddressesOnSignup(
                req.getUser().getMainEmailAddress(),
                req.getUser().getSecondaryEmailAddress()
        );

        String countryCode = userInformationValidator.validateEnteredCountryAndReturnRegionCode(req.getUser().getCountry());
        userInformationValidator.validateFarsiName(countryCode, req.getUser().getFarsiFirstName(), req.getUser().getFarsiLastName());
        String phoneNumber = userInformationValidator.validateCountryWithPhoneNumber(countryCode, req.getUser().getMobilePhoneNumber());
        userRepository.findUserByMobilePhoneNumber(phoneNumber).ifPresent(
                _ -> {
                    throw new PhoneNumberDuplicateException(className, phoneNumber);
                }
        );
        req.getUser().setMobilePhoneNumber(phoneNumber);

        if (countryCode.equals("IR"))
            if (!nationalIdValidator.execute(req.getNationalId()))
                throw new InvalidNationalIdException(className, req.getNationalId());

        if (userRepository.existsUserByNationalId(req.getNationalId()))
            throw new PersonNationalIdDuplicateException(className, req.getNationalId());

        if (!req.getUser().getPassword().equals(req.getUser().getConfirmPassword()))
            throw new PasswordAndConfirmPasswordMismatchException(className);

        String hashedPassword = passwordEncoder.encode(req.getUser().getPassword());

        return userRepository.save(User.createPerson(req, hashedPassword));
    }

    /**
     * <p>It can only be used in Company Registration flow.</p>
     *
     * @param req the incoming request to Create a Company type of User
     * @return the Company's ContactPerson
     */
    @Transactional
    public User registerCompanyAccount(CompanyUserRegisterRequestDTO req) {
        companyService.validateNationalRegistrationCode(req.getNationalRegistrationCode());

        validateEmailAddressesOnSignup(
                req.getCompanyContactPerson().getMainEmailAddress(),
                req.getCompanyContactPerson().getSecondaryEmailAddress()
        );

        String countryCode = userInformationValidator.validateEnteredCountryAndReturnRegionCode(req.getCompanyContactPerson().getCountry());
        String phoneNumber = userInformationValidator.validateCountryWithPhoneNumber(countryCode, req.getCompanyContactPerson().getMobilePhoneNumber());
        userRepository.findUserByMobilePhoneNumber(phoneNumber).ifPresent(
                _ -> {
                    throw new PhoneNumberDuplicateException(className, phoneNumber);
                }
        );
        req.getCompanyContactPerson().setMobilePhoneNumber(phoneNumber);

        if (!req.getCompanyContactPerson().getPassword().equals(req.getCompanyContactPerson().getConfirmPassword()))
            throw new PasswordAndConfirmPasswordMismatchException(className);

        String hashedPassword = passwordEncoder.encode(req.getCompanyContactPerson().getPassword());

        User companyContactPerson = User.createCompany(req, hashedPassword);

        return userRepository.save(companyContactPerson);
    }

    public void marketerMakeUserFormalOrInformal(Long userId, Boolean t, Long marketerId) {
        User user = userRepository.findById(userId).get();

        user.setIsFormal(t);
        user.setMarketerId(marketerId);

        userRepository.save(user);
    }

    public void changePassword(ChangePasswordRequestDTO req) {
        User user = userExtractor.getLoggedInUser();

        String givenCurrentPassword = req.getCurrentPassword();
        String currentPassword = user.getPassword();

        // if The User enters the Current Password correctly, goes into the IF body
        if (passwordEncoder.matches(givenCurrentPassword, currentPassword)) {
            // Check for NEW Password and its paired Confirm-Password
            if (!req.getPassword().equals(req.getConfirmPassword()))
                throw new PasswordAndConfirmPasswordMismatchException(className);

            // if the New Password the Same as the OLD Password.
            if (passwordEncoder.matches(req.getPassword(), user.getPassword()))
                throw new NewPasswordSameAsOldPasswordException(className);

            String hashedPassword = passwordEncoder.encode(req.getPassword());

            user.changePassword(hashedPassword);
            userRepository.save(user);
        } else throw new CurrentPasswordWrongException(className);
    }

    /**
     * <p>Validates Email Addresses for User, Person, CompanyAdmin and Contacts</p>
     *
     * @param mainEmailAddress      the Main Email Address
     * @param secondaryEmailAddress the Secondary Email Address as a backup
     */
    public void validateEmailAddressesOnSignup(String mainEmailAddress, String secondaryEmailAddress) {
        if (secondaryEmailAddress == null || secondaryEmailAddress.isEmpty()) {
            isValidEmailAddress(mainEmailAddress);

            if (userRepository.countsUsersByBothMailAddresses(mainEmailAddress) > 0)
                throw new EmailDuplicateException(className, mainEmailAddress);

        } else {
            isValidEmailAddress(mainEmailAddress);
            isValidEmailAddress(secondaryEmailAddress);

            if (mainEmailAddress.equalsIgnoreCase(secondaryEmailAddress))
                throw new EmailMainAndSecondaryTheSameException(className);

            if (userRepository.countsUsersByBothMailAddresses(mainEmailAddress) > 0)
                throw new EmailDuplicateException(className, mainEmailAddress);

            if (userRepository.countsUsersByBothMailAddresses(secondaryEmailAddress) > 0)
                throw new EmailDuplicateException(className, secondaryEmailAddress);

        }
    }

    private void isValidEmailAddress(String email) {
        if (!EmailValidator.getInstance().isValid(email))
            throw new EmailAddressFormatException(className, email);
    }

}
