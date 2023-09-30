package com.halcyon.afterglow.repository;

import com.halcyon.afterglow.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE Comment comment SET comment.value = ?1 WHERE comment.id = ?2")
    void updateValueById(String value, Long id);

    List<Comment> findAllByPostId(Long postId);
}