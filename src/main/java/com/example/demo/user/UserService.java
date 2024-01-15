package com.example.demo.user;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void signup(String username, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setPassowrd(passwordEncoder.encode(password));
        this.userRepository.save(user);
    }
}
