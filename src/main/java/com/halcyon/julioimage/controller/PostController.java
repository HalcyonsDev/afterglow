package com.halcyon.julioimage.controller;

import com.halcyon.julioimage.dto.post.NewPostDto;
import com.halcyon.julioimage.model.Post;
import com.halcyon.julioimage.service.post.PostService;
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
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Tag(name = "Posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    @Operation(
            summary = "create post",
            description = "create post"
    )
    public ResponseEntity<Post> create(@RequestBody @Valid NewPostDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Post createdPost = postService.create(dto);
        return ResponseEntity.ok(createdPost);
    }

    @DeleteMapping("/delete/{postId}")
    @Operation(
            summary = "find and delete post by its id",
            description = "delete post by id"
    )
    public ResponseEntity<String> delete(@PathVariable Long postId) {
        String response = postService.deleteById(postId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}")
    @Operation(
            summary = "find and return post by its id",
            description = "get post"
    )
    public ResponseEntity<Post> getById(@PathVariable Long postId) {
        Post post = postService.findById(postId);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/my")
    @Operation(
            summary = "find and return user owned posts",
            description = "get posts by owner"
    )
    public ResponseEntity<List<Post>> getAllByOwner() {
        List<Post> posts = postService.findAllByOwner();
        System.out.println(posts);
        return ResponseEntity.ok(posts);
    }

    @PatchMapping("/update-name/{postId}")
    @Operation(
            summary = "find post and update name for it by id",
            description = "update post name by id"
    )
    public ResponseEntity<Post> updateNameById(@PathVariable Long postId, @RequestParam String name) {
        Post updatedPost = postService.updateNameById(postId, name);
        return ResponseEntity.ok(updatedPost);
    }

    @PatchMapping("/update-description/{postId}")
    @Operation(
            summary = "find post and update description for it by id",
            description = "update post description by id"
    )
    public ResponseEntity<Post> updateDescriptionById(@PathVariable Long postId, @RequestParam String description) {
        Post updatedPost = postService.updateDescriptionById(postId, description);
        return ResponseEntity.ok(updatedPost);
    }
}