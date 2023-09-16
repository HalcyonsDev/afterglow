package com.halcyon.julioimage.controller;

import com.halcyon.julioimage.dto.like.NewLikeDto;
import com.halcyon.julioimage.model.Like;
import com.halcyon.julioimage.service.like.LikeService;
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
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<Like> create(@RequestBody @Valid NewLikeDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Like createdLike = likeService.create(dto);
        return ResponseEntity.ok(createdLike);
    }

    @DeleteMapping("/delete/{likeId}")
    public ResponseEntity<String> delete(@PathVariable Long likeId) {
        String response = likeService.deleteById(likeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{likeId}")
    public ResponseEntity<Like> getById(@PathVariable Long likeId) {
        Like like = likeService.findById(likeId);
        return ResponseEntity.ok(like);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Like>> findAllByPostId(@PathVariable Long postId) {
        List<Like> likes = likeService.findAllByPostId(postId);
        return ResponseEntity.ok(likes);
    }
}