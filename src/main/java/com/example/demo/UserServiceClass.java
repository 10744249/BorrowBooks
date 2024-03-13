package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceClass {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceClass(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Register a new user
    public boolean registerUser(User user) {
        // Check if phone number is already in use
        Optional<User> existingUser = userRepository.findByPhoneNumber(user.getPhoneNumber());
        if (existingUser.isPresent()) {
            // Phone number already in use
            return false;
        }

        // Encrypt the password before saving to database
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        // Set the registration and last login time to the current time
        LocalDateTime now = LocalDateTime.now();
        user.setRegistrationTime(now);
        user.setLastLoginTime(now);

        // Save user to the database
        userRepository.save(user);
        return true;
    }

    // Authenticate user login
    public boolean authenticateUser(User user) {
        // Retrieve the user by phone number
        Optional<User> foundUser = userRepository.findByPhoneNumber(user.getPhoneNumber());
        if (!foundUser.isPresent()) {
            return false; // User not found
        }

        // Check the password
        return passwordEncoder.matches(user.getPassword(), foundUser.get().getPassword());
    }
}
