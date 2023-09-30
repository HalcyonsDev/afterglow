package com.halcyon.afterglow.repository;

import com.halcyon.afterglow.model.Image;
import com.halcyon.afterglow.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImageRepository extends JpaRepository<Image, Long> {
    List<Image> findImagesByPost(Post post);
}