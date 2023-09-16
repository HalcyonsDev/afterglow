package com.halcyon.julioimage.repository;

import com.halcyon.julioimage.model.Subscription;
import com.halcyon.julioimage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISubscriptionRepository extends JpaRepository<Subscription, Long> {
    boolean existsByOwnerAndTarget(User owner, User target);
    Subscription findByOwnerAndTarget(User owner, User target);
    List<Subscription> findAllByOwner(User owner);
    List<Subscription> findAllByTarget(User target);
}