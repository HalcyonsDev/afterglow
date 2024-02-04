package com.halcyon.afterglow.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "ratings")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @NonNull
    private RatingType type;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @NonNull
    private User owner;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    @NonNull
    private Post post;

    @PrePersist
    private void onCreate() {
        createdAt = Instant.now();
    }
}
