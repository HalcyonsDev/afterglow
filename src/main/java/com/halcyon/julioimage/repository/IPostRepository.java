package com.halcyon.julioimage.repository;

import com.halcyon.julioimage.model.Post;
import com.halcyon.julioimage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {
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