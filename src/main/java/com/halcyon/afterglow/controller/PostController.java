package com.halcyon.afterglow.controller;

import com.halcyon.afterglow.dto.post.NewPostDto;
import com.halcyon.afterglow.model.Post;
import com.halcyon.afterglow.service.post.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Tag(name = "Posts")
@Validated
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
        return ResponseEntity.ok(posts);
    }

    @PatchMapping("/{postId}/update-name")
    @Operation(
            summary = "find post and update name for it by id",
            description = "update post's name by id"
    )
    public ResponseEntity<Post> updateNameById(
            @PathVariable Long postId,
            @RequestParam
            @Size(min = 2, max = 50, message = "Name must be more than 1 character and less than 50 characters.") String value) {
        Post updatedPost = postService.updateNameById(postId, value);
        return ResponseEntity.ok(updatedPost);
    }

    @PatchMapping("/{postId}/update-description")
    @Operation(
            summary = "find post and update description for it by id",
            description = "update post's description by id"
    )
    public ResponseEntity<Post> updateDescriptionById(
            @PathVariable Long postId,
            @RequestParam
            @Size(min = 4, max = 64, message = "Description must be more than 3 characters and less than 64 characters.") String value) {
        Post updatedPost = postService.updateDescriptionById(postId, value);
        return ResponseEntity.ok(updatedPost);
    }
}