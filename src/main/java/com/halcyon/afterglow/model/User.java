package com.halcyon.afterglow.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "email")
    private String email;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "description")
    private String description;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "owner")
    @JsonBackReference
    private List<Post> posts;

    @OneToMany(mappedBy = "owner")
    @JsonBackReference
    private List<Comment> comments;

    @OneToMany(mappedBy = "owner")
    @JsonBackReference
    private List<Like> likes;

    @OneToMany(mappedBy = "owner")
    @JsonBackReference
    private List<Subscription> subscriptions;

    @OneToMany(mappedBy = "target")
    @JsonBackReference
    private List<Subscription> subscribers;

    @PrePersist
    private void onCreate() {
        createdAt = Instant.now();
    }
}