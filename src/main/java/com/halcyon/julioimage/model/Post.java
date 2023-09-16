package com.halcyon.julioimage.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "name")
    @NonNull
    private String name;

    @Column(name = "description")
    @NonNull
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @JsonManagedReference
    private User owner;

    @OneToMany(mappedBy = "post")
    @JsonBackReference
    private List<Comment> comments;

    @OneToMany(mappedBy = "post")
    @JsonBackReference
    private List<Like> likes;

    @OneToMany(mappedBy = "post")
    @JsonBackReference
    private List<Image> images;

    @PrePersist
    private void onCreate() {
        createdAt = Instant.now();
    }
}