package com.halcyon.afterglow.repository;

import com.halcyon.afterglow.model.Dislike;
import com.halcyon.afterglow.model.Post;
import com.halcyon.afterglow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDislikeRepository extends JpaRepository<Dislike, Long> {
    List<Dislike> findAllByPostId(Long postId);
    boolean existsByOwnerAndPost(User owner, Post post);
}