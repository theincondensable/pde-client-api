package com.pde.business.service.auth;

import com.pde.business.exceptions.auth.JwtTokenExpiredException;
import com.pde.business.repository.auth.TokenRepository;
import com.pde.global.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * @author abbas
 */
@Service
@AllArgsConstructor
public class JwtService {

    private final String className = this.getClass().getSimpleName();

    private final TokenRepository tokenRepository;

    public void validateJwtTokenExpiration(Long userId) throws BusinessException {
        tokenRepository.findByUserId(userId).ifPresentOrElse(
                token -> {
                    Instant now = Instant.now();
                    if (token.getActiveTime().isBefore(now)) {
                        tokenRepository.delete(token);
                        throw new JwtTokenExpiredException(className);
                    }
                    token.setActiveTime(now.plus(TokensEnum.JWT_TOKEN_EXPIRES_IN_MINUTES, ChronoUnit.MINUTES));
                    tokenRepository.save(token);
                }, () -> {
                    throw new JwtTokenExpiredException(className);
                }
        );
    }

}
