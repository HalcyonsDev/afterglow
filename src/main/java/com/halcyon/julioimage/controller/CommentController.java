package com.halcyon.julioimage.controller;

import com.halcyon.julioimage.dto.comment.NewCommentDto;
import com.halcyon.julioimage.model.Comment;
import com.halcyon.julioimage.service.comment.CommentService;
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
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@Tag(name = "Comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    @Operation(
            summary = "create comment",
            description = "create comment"
    )
    public ResponseEntity<Comment> create(@RequestBody @Valid NewCommentDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Comment createdComment = commentService.create(dto);
        return ResponseEntity.ok(createdComment);
    }

    @DeleteMapping("delete/{commentId}")
    @Operation(
            summary = "find and delete comment by its id",
            description = "delete comment by id"
    )
    public ResponseEntity<String> delete(@PathVariable Long commentId) {
        String response = commentService.deleteById(commentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{commentId}")
    @Operation(
            summary = "find and return comment by its id",
            description = "get comment"
    )
    public ResponseEntity<Comment> getById(@PathVariable Long commentId) {
        Comment comment = commentService.findById(commentId);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/post/{postId}")
    @Operation(
            summary = "find and return all post's comments by its id",
            description = "get all comments by post id"
    )
    public ResponseEntity<List<Comment>> getAllByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentService.findAllByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @PatchMapping("/update/{commentId}")
    @Operation(
            summary = "find and update comment's value by its id",
            description = "update comment's value by id"
    )
    public ResponseEntity<Comment> updateValueById(@PathVariable Long commentId, @RequestParam String value) {
        Comment updatedComment = commentService.updateValueById(commentId, value);
        return ResponseEntity.ok(updatedComment);
    }
}