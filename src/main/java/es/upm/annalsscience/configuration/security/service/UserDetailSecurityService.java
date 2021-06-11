package es.upm.annalsscience.configuration.security.service;

import es.upm.annalsscience.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserDetailSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    private UserDetailSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
        return userRepository.findByEmail(user)
                .map(u -> userBuilder(u.getEmail(), u.getPassword()))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    private User userBuilder(String username, String password) {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        List<GrantedAuthority> authorities = Collections.emptyList();
        return new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
