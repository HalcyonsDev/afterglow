package com.halcyon.julioimage.controller;

import com.halcyon.julioimage.dto.susbcription.NewSubscriptionDto;
import com.halcyon.julioimage.model.Subscription;
import com.halcyon.julioimage.service.subscription.SubscriptionService;
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
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<Subscription> create(@RequestBody @Valid NewSubscriptionDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Subscription createdSubscription = subscriptionService.create(dto.getTargetEmail());
        return ResponseEntity.ok(createdSubscription);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody @Valid NewSubscriptionDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        String response = subscriptionService.deleteByTargetEmail(dto.getTargetEmail());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Subscription>> getUserSubscriptions() {
        List<Subscription> subscriptions = subscriptionService.findUserSubscriptions();
        return ResponseEntity.ok(subscriptions);
    }

    @GetMapping("/user/subscribers")
    public ResponseEntity<List<Subscription>> getUserSubscribers() {
        List<Subscription> subscribers = subscriptionService.findUserSubscribers();
        return ResponseEntity.ok(subscribers);
    }
}