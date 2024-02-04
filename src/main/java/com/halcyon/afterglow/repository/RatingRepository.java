package com.halcyon.afterglow.repository;

import com.halcyon.afterglow.model.Post;
import com.halcyon.afterglow.model.Rating;
import com.halcyon.afterglow.model.RatingType;
import com.halcyon.afterglow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    boolean existsByOwnerAndPost(User owner, Post post);
    List<Rating> findAllByPostIdAndType(Long postId, RatingType type);
}
