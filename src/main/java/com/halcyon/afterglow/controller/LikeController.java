package com.halcyon.afterglow.controller;

import com.halcyon.afterglow.dto.like.NewLikeDto;
import com.halcyon.afterglow.model.Like;
import com.halcyon.afterglow.service.like.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
@Tag(name = "Likes")
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    @Operation(
            summary = "create like",
            description = "create like"
    )
    public ResponseEntity<Like> create(@RequestBody @Valid NewLikeDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Like createdLike = likeService.create(dto);
        return ResponseEntity.ok(createdLike);
    }

    @DeleteMapping("/delete/{likeId}")
    @Operation(
            summary = "find and delete like by its id",
            description = "delete like by id"
    )
    public ResponseEntity<String> delete(@PathVariable Long likeId) {
        String response = likeService.deleteById(likeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{likeId}")
    @Operation(
            summary = "find and return like by its id",
            description = "get like"
    )
    public ResponseEntity<Like> getById(@PathVariable Long likeId) {
        Like like = likeService.findById(likeId);
        return ResponseEntity.ok(like);
    }

    @GetMapping("/post/{postId}")
    @Operation(
            summary = "find and return all post's likes by its id",
            description = "get all likes by post id"
    )
    public ResponseEntity<List<Like>> getAllByPostId(@PathVariable Long postId) {
        List<Like> likes = likeService.findAllByPostId(postId);
        return ResponseEntity.ok(likes);
    }
}