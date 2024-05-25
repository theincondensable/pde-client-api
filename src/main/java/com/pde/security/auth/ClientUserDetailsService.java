package com.pde.security.auth;

import com.pde.business.exceptions.auth.user.UserNotFoundWithEmailException;
import com.pde.business.model.auth.User;
import com.pde.business.repository.auth.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public ClientUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByAnyOfBothEmailAddresses(email).orElseThrow(
                () -> new UserNotFoundWithEmailException(this.getClass().getSimpleName() ,email)
        );

        return new ClientUserDetails(user);
    }

}
