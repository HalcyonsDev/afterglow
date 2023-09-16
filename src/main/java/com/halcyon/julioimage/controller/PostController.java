package com.halcyon.julioimage.controller;

import com.halcyon.julioimage.dto.post.NewPostDto;
import com.halcyon.julioimage.model.Post;
import com.halcyon.julioimage.service.post.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<Post> create(@RequestBody @Valid NewPostDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Post createdPost = postService.create(dto);
        return ResponseEntity.ok(createdPost);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> delete(@PathVariable Long postId) {
        String response = postService.deleteById(postId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getById(@PathVariable Long postId) {
        Post post = postService.findById(postId);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/my")
    public ResponseEntity<List<Post>> getAllByOwner() {
        List<Post> posts = postService.findAllByOwner();
        System.out.println(posts);
        return ResponseEntity.ok(posts);
    }

    @PatchMapping("/update-name/{postId}")
    public ResponseEntity<Post> updateNameById(@PathVariable Long postId, @RequestParam String name) {
        Post updatedPost = postService.updateNameById(postId, name);
        return ResponseEntity.ok(updatedPost);
    }

    @PatchMapping("/update-description/{postId}")
    public ResponseEntity<Post> updateDescriptionById(@PathVariable Long postId, @RequestParam String description) {
        Post updatedPost = postService.updateDescriptionById(postId, description);
        return ResponseEntity.ok(updatedPost);
    }
}