package com.halcyon.afterglow.service.user;

import com.halcyon.afterglow.dto.user.UpdateUserDto;
import com.halcyon.afterglow.model.User;
import com.halcyon.afterglow.repository.UserRepository;
import com.halcyon.afterglow.security.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User create(User user) {
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this id not found."));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this email not found."));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User updateData(UpdateUserDto dto) {
        User user = findByEmail(getAuth().getEmail());

        String firstname = dto.getFirstname();
        String lastname = dto.getLastname();
        String description = dto.getDescription();

        userRepository.updateDataByEmail(firstname, lastname, description, user.getEmail());

        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setDescription(description);

        return user;
    }

    private JwtAuthentication getAuth() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}