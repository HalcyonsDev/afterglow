package com.halcyon.afterglow.repository;

import com.halcyon.afterglow.model.Subscription;
import com.halcyon.afterglow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    boolean existsByOwnerAndTarget(User owner, User target);
    Subscription findByOwnerAndTarget(User owner, User target);
    List<Subscription> findAllByOwner(User owner);
    List<Subscription> findAllByTarget(User target);
}