package com.halcyon.afterglow.controller;

import com.halcyon.afterglow.dto.susbcription.NewSubscriptionDto;
import com.halcyon.afterglow.model.Subscription;
import com.halcyon.afterglow.service.subscription.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
@Tag(name = "Subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping
    @Operation(
            summary = "create subscription",
            description = "create subscription"
    )
    public ResponseEntity<Subscription> create(@RequestBody @Valid NewSubscriptionDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Subscription createdSubscription = subscriptionService.create(dto.getTargetEmail());
        return ResponseEntity.ok(createdSubscription);
    }

    @DeleteMapping("/delete")
    @Operation(
            summary = "find and delete subscription by its id",
            description = "delete subscription by id"
    )
    public ResponseEntity<String> delete(@RequestBody @Valid NewSubscriptionDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        String response = subscriptionService.deleteByTargetEmail(dto.getTargetEmail());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    @Operation(
            summary = "find and return user subscriptions",
            description = "get user subscriptions"
    )
    public ResponseEntity<List<Subscription>> getUserSubscriptions() {
        List<Subscription> subscriptions = subscriptionService.findUserSubscriptions();
        return ResponseEntity.ok(subscriptions);
    }

    @GetMapping("/user/subscribers")
    @Operation(
            summary = "find and return user subscribers",
            description = "get user subscribers"
    )
    public ResponseEntity<List<Subscription>> getUserSubscribers() {
        List<Subscription> subscribers = subscriptionService.findUserSubscribers();
        return ResponseEntity.ok(subscribers);
    }
}