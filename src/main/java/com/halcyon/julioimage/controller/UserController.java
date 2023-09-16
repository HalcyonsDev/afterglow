package com.halcyon.julioimage.controller;

import com.halcyon.julioimage.dto.user.UpdateUserDto;
import com.halcyon.julioimage.model.User;
import com.halcyon.julioimage.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getById(@PathVariable Long userId) {
        User user = userService.findById(userId);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/update")
    public ResponseEntity<User> updateData(@RequestBody @Valid UpdateUserDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        User updatedUser = userService.updateData(dto);
        return ResponseEntity.ok(updatedUser);
    }
}