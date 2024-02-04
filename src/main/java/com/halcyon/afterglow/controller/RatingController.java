package com.halcyon.afterglow.controller;

import com.halcyon.afterglow.dto.rating.NewRatingDto;
import com.halcyon.afterglow.model.Rating;
import com.halcyon.afterglow.service.rating.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/v1/ratings")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @PostMapping
    public ResponseEntity<Rating> create(@RequestBody @Valid NewRatingDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Rating rating = ratingService.create(dto);
        return ResponseEntity.ok(rating);
    }

    @DeleteMapping("/{ratingId}/delete")
    public ResponseEntity<String> delete(@PathVariable Long ratingId) {
        String response = ratingService.deleteById(ratingId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{ratingId}")
    public ResponseEntity<Rating> getById(@PathVariable Long ratingId) {
        Rating rating = ratingService.findById(ratingId);
        return ResponseEntity.ok(rating);
    }

    @GetMapping("/likes/{postId}")
    public ResponseEntity<List<Rating>> getLikesByPost(@PathVariable Long postId) {
        List<Rating> likes = ratingService.findLikesByPostId(postId);
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/dislikes/{postId}")
    public ResponseEntity<List<Rating>> getDislikesByPost(@PathVariable Long postId) {
        List<Rating> dislikes = ratingService.findDislikesByPostId(postId);
        return ResponseEntity.ok(dislikes);
    }
}
