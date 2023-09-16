package com.halcyon.julioimage.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @NonNull
    private User owner;

    @ManyToOne
    @JoinColumn(name = "target_id", referencedColumnName = "id")
    @NonNull
    private User target;
}