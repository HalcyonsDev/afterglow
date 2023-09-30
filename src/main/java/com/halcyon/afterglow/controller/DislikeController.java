package com.halcyon.afterglow.controller;

import com.halcyon.afterglow.dto.dislike.NewDislikeDto;
import com.halcyon.afterglow.model.Dislike;
import com.halcyon.afterglow.service.dislike.DislikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dislikes")
@RequiredArgsConstructor
@Tag(name = "Dislikes")
public class DislikeController {
    private final DislikeService dislikeService;

    @PostMapping
    @Operation(
            summary = "create dislike",
            description = "create dislike"
    )
    public ResponseEntity<Dislike> create(@RequestBody NewDislikeDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Dislike createdDislike = dislikeService.create(dto);
        return ResponseEntity.ok(createdDislike);
    }

    @DeleteMapping("/delete/{dislikeId}")
    @Operation(
            summary = "find and delete dislike by its id",
            description = "delete dislike by id"
    )
    public ResponseEntity<String> delete(@PathVariable Long dislikeId) {
        String response = dislikeService.deleteById(dislikeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{dislikeId}")
    @Operation(
            summary = "find and return dislike by its id",
            description = "get dislike"
    )
    public ResponseEntity<Dislike> getById(@PathVariable Long dislikeId) {
        Dislike dislike = dislikeService.findById(dislikeId);
        return ResponseEntity.ok(dislike);
    }

    @GetMapping("/post/{postId}")
    @Operation(
            summary = "find and return all post's dislikes by its id",
            description = "get all dislikes by post id"
    )
    public ResponseEntity<List<Dislike>> getAllByPostId(@PathVariable Long postId) {
        List<Dislike> dislikes = dislikeService.findAllByPostId(postId);
        return ResponseEntity.ok(dislikes);
    }
}