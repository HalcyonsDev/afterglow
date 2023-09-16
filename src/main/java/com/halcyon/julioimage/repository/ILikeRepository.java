package com.halcyon.julioimage.repository;

import com.halcyon.julioimage.model.Like;
import com.halcyon.julioimage.model.Post;
import com.halcyon.julioimage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILikeRepository extends JpaRepository<Like, Long> {
    boolean existsByOwnerAndPost(User owner, Post post);
    List<Like> findAllByPostId(Long postId);
}