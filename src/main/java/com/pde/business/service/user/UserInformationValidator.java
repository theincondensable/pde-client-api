package com.pde.business.service.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.mysql.cj.util.StringUtils;
import com.pde.business.exceptions.auth.user.EmailAddressFormatException;
import com.pde.business.exceptions.auth.user.InvalidPhoneNumberException;
import com.pde.business.exceptions.auth.user.PersonFarsiFirstNameNullException;
import com.pde.business.exceptions.auth.user.PersonFarsiLastNameNullException;
import com.pde.business.service.user.types.RegionCodeModel;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @author abbas
 */
@Service
public class UserInformationValidator {

    private final String className = this.getClass().getSimpleName();

    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    /**
     * <p>User enters its Country, by having this, we can extract the Country ALPHA-2 Region Code.</p>
     * <p>At first, the Country is being evaluated, then its ALPHA2 Code will be returned.</p>
     *
     * @return Country's ALPHA2 Code to be used in PhoneNumber validation
     */
    public String validateEnteredCountryAndReturnRegionCode(String country) {
        String alpha2 = null;
        char[] countryChars = country.toCharArray();
        countryChars[0] = Character.toUpperCase(country.charAt(0));
        final String countryName = String.valueOf(countryChars);

        if (countryName.contains("Iran"))
            return "IR";
        if (countryName.equals("China"))
            return "CN";

        ObjectMapper mapper = new ObjectMapper();
        try {
            List<RegionCodeModel> regionCodeModels = mapper.readValue(new File("src/main/resources/country-phones-codes.json"), new TypeReference<>() {
            });
            RegionCodeModel regionCode = regionCodeModels.stream().filter(code -> code.getName().equals(countryName)).findFirst().get();
            alpha2 = regionCode.getAlpha2();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return alpha2;
    }

    /**
     * <p>
     * Based on the Country that User has selected, the PhoneNumber will be validated and then formatted.<br>
     * if the given PhoneNumber does not meet the given Country's regulations, InvalidPhoneNumberException is thrown.
     * </p>
     *
     * @param regionCode  is extracted using the validateEnteredCountryAndReturnRegionCode function
     * @param phoneNumber is given by the User request
     * @return the formatted PhoneNumber proper for the RegionCode
     */
    public String validateCountryWithPhoneNumber(String regionCode, String phoneNumber) {
        try {
            Phonenumber.PhoneNumber number = phoneNumberUtil.parse(phoneNumber, regionCode);
            if (phoneNumberUtil.isValidNumberForRegion(number, regionCode))
                return phoneNumberUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164);
            else throw new InvalidPhoneNumberException(className, phoneNumber);
        } catch (NumberParseException e) {
            throw new InvalidPhoneNumberException(className, phoneNumber);
        }
    }

    public void validateFarsiName(String countryCode, String farsiFirstName, String farsiLastName) {
        if (countryCode.equals("IR")) {
            if (StringUtils.isNullOrEmpty(farsiFirstName))
                throw new PersonFarsiFirstNameNullException(className);

            if (StringUtils.isNullOrEmpty(farsiLastName))
                throw new PersonFarsiLastNameNullException(className);
        }
    }

    private void isValidEmailAddress(String email) {
        if (!EmailValidator.getInstance().isValid(email))
            throw new EmailAddressFormatException(className, email);
    }

}
