package com.pde.business.service.auth;

import com.pde.business.exceptions.auth.InactiveUserException;
import com.pde.business.exceptions.auth.otp.*;
import com.pde.business.model.auth.Otp;
import com.pde.business.model.auth.User;
import com.pde.business.repository.auth.OTPRepository;
import com.pde.business.service.user.UserService;
import com.pde.web.dto.auth.login.OtpValidationRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Random;

/**
 * @author abbas
 */
@Service
@AllArgsConstructor
public class OtpService {

    private final String className = this.getClass().getSimpleName();

    private final UserService userService;
    private final OTPRepository otpRepository;

    public Integer sendOtpCodeToPhoneNumber(String phoneNumber) {
        User user = userService.findUserByPhoneNumber(phoneNumber);

//        if (!user.getIsActive()) throw new InactiveUserException(className);

        return otpRepository.findOtpByUserId(user.getId()).map(
                // if an OTP Code EXISTS for the User's MobilePhoneNumber
                otp -> {
                    validateInactiveUntil(otp);
                    // if the OTP is NOT ACTIVE, the previous one will be deleted and a NEW ONE will be sent to the User.
                    if (otp.getActiveUntil().plus(TokensEnum.OTP_EXPIRES_IN_MINUTES, ChronoUnit.MINUTES).isAfter(Instant.now())) {
                        otpRepository.delete(otp);
                        return generateOtpCodeAndSaveToUserEntity(user);
                    }
                    // if the OTP is still ACTIVE, which is about several minutes only, an Exception is Thrown.
                    throw new OtpIsAlreadySentException(className);
                }
        ).orElseGet(
                // if there is NO OTP Code for User's mobilePhoneNumber, an OTP will be GENERATED and SENT.
                () -> generateOtpCodeAndSaveToUserEntity(user)
        );

//        Timer smsTimer = new Timer();
//        TimerTask smsTimerTask = new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("OTP Code is sent via SMS.");
//            }
//        };
//        smsTimer.schedule(smsTimerTask, Date.from(Instant.now()));

//        BackgroundJob.enqueue(() -> mailService.sendOtpCodeToCustomer(email, otpCode));
    }

    public Boolean validateEnteredOtpCode(OtpValidationRequestDTO otpDto) {
        return otpRepository.findByUserPhoneNumber(otpDto.getPhoneNumber()).map(
                otp -> validate(otp, otpDto.getOtpCode())
        ).orElseThrow(() -> new OtpNotRequestedException(className));
    }

    private Integer generateOtpCodeAndSaveToUserEntity(User user) {
        final int start = 1000;
        final int end = 9999;
        Random otpRandom = new Random();

        Integer code = otpRandom.nextInt(start, end);
        Otp otp = new Otp(
                code,
                0,
                Instant.now().plus(TokensEnum.OTP_EXPIRES_IN_MINUTES, ChronoUnit.MINUTES),
                null,
                user
        );
        return otpRepository.save(otp).getOtpCode();
    }

    private Boolean validate(Otp otp, Integer givenOtpCode) {
        validateInactiveUntil(otp);
        validateExpiration(otp);
        validateMaxTry(otp);
        validateCorrectness(otp, givenOtpCode);

        return true;
    }

    private void validateExpiration(Otp otp) {
        if (Instant.now().isAfter(otp.getCreatedAt().plus(TokensEnum.OTP_EXPIRES_IN_MINUTES, ChronoUnit.MINUTES))) {
            otpRepository.delete(otp);
            throw new OtpTokenIsExpiredException(className);
        }
    }

    private void validateMaxTry(Otp otp) {
        if (otp.getOtpCounter() >= TokensEnum.OTP_MAX_TRY) {
            otp.setInactiveUntil(Instant.now().plus(TokensEnum.OTP_SUSPENSION_IN_MINUTES, ChronoUnit.MINUTES));
            otpRepository.save(otp);
            throw new OtpMaxTryViolationException(className, Date.from(otp.getInactiveUntil()).toString());
        }
    }

    private void validateCorrectness(Otp otp, Integer givenOtpCode) {
        if (!givenOtpCode.equals(otp.getOtpCode())) {
            otp.increaseOtpCounter();
            otpRepository.save(otp);
            throw new OtpMismatchException(className, String.valueOf(givenOtpCode));
        }
    }

    /**
     * <p>if inactiveUntil is NOT NULL it means that the OTP is suspended.</p>
     * <p>if the inactiveUntil is AFTER NOW, it means that the Login with OTP Approach, is STILL suspended.</p>
     * @param otp The OTP Code Object
     */
    private void validateInactiveUntil(Otp otp) {
        if (otp.getInactiveUntil() != null) {
            if (otp.getInactiveUntil().isAfter(Instant.now()))
                throw new OtpMaxTryViolationException(className, LocalDateTime.ofInstant(otp.getInactiveUntil(), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm:ss")));
        }
    }

}
