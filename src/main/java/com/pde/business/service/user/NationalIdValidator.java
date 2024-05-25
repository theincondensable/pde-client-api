package com.pde.business.service.user;

import org.springframework.stereotype.Service;

/**
 * @author abbas
 */
@Service
public class NationalIdValidator {

    public boolean execute(String code) {
        if (isValidCode(code)) {
            String subMid = getSubMidNumbers(code, 10);

            int getNum = 0;
            for (int i = 1; i < 10; i++) {
                getNum += Integer.parseInt(getSubMidNumbers(code, i)) * (11 - i);
            }

            int modulus = (getNum % 11);

            return ((modulus < 2) && (subMid.equals(String.valueOf(modulus)))) || ((modulus >= 2) && (subMid.equals(String.valueOf(11 - modulus))));
        }

        return false;
    }

    private boolean isValidCode(String nationalCode) {
        return (nationalCode.matches("\\d+") && nationalCode.length() == 10);
    }

    private String getSubMidNumbers(String number, int start) {
        return number.substring(start - 1, start - 1 + 1);
    }
}
