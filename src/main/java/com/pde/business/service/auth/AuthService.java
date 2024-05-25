package com.pde.business.service.auth;

import com.pde.business.exceptions.auth.InactiveUserException;
import com.pde.business.exceptions.auth.PasswordNotCorrectException;
import com.pde.business.model.auth.Token;
import com.pde.business.model.auth.User;
import com.pde.business.repository.auth.TokenRepository;
import com.pde.business.service.user.UserService;
import com.pde.security.auth.ClientUserDetails;
import com.pde.security.auth.jwt.JwtUtil;
import com.pde.web.dto.auth.LoginWithEmailRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author abbas
 */
@Service
@AllArgsConstructor
public class AuthService {

    private final String className = this.getClass().getSimpleName();

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Transactional
    public String login(LoginWithEmailRequestDTO req) {
        User user = userService.findUserByEmailAddress(req.getEmail());

//        if (!user.getIsActive()) throw new InactiveUserException(className);

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword()))
            throw new PasswordNotCorrectException(className);

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getEmail(),
                        req.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
        ClientUserDetails userDetails = (ClientUserDetails) auth.getPrincipal();
        AtomicReference<String> jwtToken = new AtomicReference<>();
        jwtToken.set(jwtUtil.generateJwtToken(userDetails));

        tokenRepository.findByUserId(user.getId()).ifPresentOrElse(
                token -> {
                    Instant now = Instant.now();
                    jwtToken.set(token.getJwt());
                    token.setActiveTime(now, req.getRememberMe());
                    tokenRepository.save(token);
                }, () -> {
                    Instant now = Instant.now();
                    Token userToken = new Token();
                    userToken.setUser(user);
                    userToken.setJwt(jwtToken.get());
                    userToken.setActiveTime(now, req.getRememberMe());
                    tokenRepository.save(userToken);
                }
        );

        return jwtToken.get();
    }

    public void logout() {

    }

}
