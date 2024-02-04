package com.halcyon.afterglow.repository;

import com.halcyon.afterglow.model.Post;
import com.halcyon.afterglow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE Post post SET post.name = ?1 WHERE post.id = ?2")
    void updateNameById(String name, Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Post post SET post.description = ?1 WHERE post.id = ?2")
    void updateDescriptionById(String description, Long id);

    List<Post> findPostsByOwner(User owner);
}