package com.halcyon.afterglow.service.subscription;

import com.halcyon.afterglow.model.Subscription;
import com.halcyon.afterglow.model.User;
import com.halcyon.afterglow.repository.SubscriptionRepository;
import com.halcyon.afterglow.service.auth.AuthService;
import com.halcyon.afterglow.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;
    private final AuthService authService;

    public Subscription create(String targetEmail) {
        User owner = userService.findByEmail(authService.getAuthInfo().getEmail());
        User target = userService.findByEmail(targetEmail);

        if (subscriptionRepository.existsByOwnerAndTarget(owner, target)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Subscription already exists!");
        }

        Subscription subscription = new Subscription(owner, target);
        return subscriptionRepository.save(subscription);
    }

    public String deleteByTargetEmail(String targetEmail) {
        User owner = userService.findByEmail(authService.getAuthInfo().getEmail());
        User target = userService.findByEmail(targetEmail);

        Subscription subscription = subscriptionRepository.findByOwnerAndTarget(owner, target);
        subscriptionRepository.delete(subscription);

        return "Subscription deleted successfully.";
    }

    public List<Subscription> findUserSubscriptions() {
        User owner = userService.findByEmail(authService.getAuthInfo().getEmail());
        return subscriptionRepository.findAllByOwner(owner);
    }

    public List<Subscription> findUserSubscribers() {
        User target = userService.findByEmail(authService.getAuthInfo().getEmail());
        return subscriptionRepository.findAllByTarget(target);
    }
}