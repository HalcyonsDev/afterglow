package com.halcyon.afterglow.controller;

import com.halcyon.afterglow.dto.user.UpdateUserDto;
import com.halcyon.afterglow.model.User;
import com.halcyon.afterglow.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    @Operation(
            summary = "find and return user by his id",
            description = "get user by id"
    )
    public ResponseEntity<User> getById(@PathVariable Long userId) {
        User user = userService.findById(userId);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/update")
    @Operation(
            summary = "find user and update data for him",
            description = "update user data"
    )
    public ResponseEntity<User> updateData(@RequestBody @Valid UpdateUserDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        User updatedUser = userService.updateData(dto);
        return ResponseEntity.ok(updatedUser);
    }
}