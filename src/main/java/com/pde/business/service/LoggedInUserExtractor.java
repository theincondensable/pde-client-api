package com.pde.business.service;

import com.pde.business.exceptions.auth.user.UserNotFoundWithEmailException;
import com.pde.business.model.auth.User;
import com.pde.business.repository.auth.UserRepository;
import com.pde.security.auth.ClientUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public final class LoggedInUserExtractor {

    private final UserRepository userRepository;

    public LoggedInUserExtractor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long getLoggedInUserId() {
        Object loggedInUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (loggedInUser instanceof ClientUserDetails)
            return ((ClientUserDetails) loggedInUser).getId();

        // todo: CHANGE THIS EXCEPTION TO USER_NOT_LOGGED_IN_EXCEPTION
        throw new UserNotFoundWithEmailException(this.getClass().getSimpleName(), "getLoggedInUserId() returned null");
    }

    public User getLoggedInUser() {
        return userRepository.findById(getLoggedInUserId()).get();
    }

}