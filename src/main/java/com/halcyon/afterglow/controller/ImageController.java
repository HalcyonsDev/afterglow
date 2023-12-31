package com.halcyon.afterglow.controller;

import com.halcyon.afterglow.model.Image;
import com.halcyon.afterglow.service.image.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
@Tag(name = "Images")
public class ImageController {
    private final HttpServletRequest request;
    private final ImageService imageService;

    @PostMapping("/upload/{postId}")
    @Operation(
            summary = "upload and save image in post by its id",
            description = "upload image"
    )
    public ResponseEntity<Image> create(@PathVariable Long postId, @RequestParam("file") MultipartFile file) throws IOException {
        if (Objects.equals(file.getContentType(), "image/jpeg") &&
                Objects.equals(file.getContentType(), "image/png")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image type should be jpeg/png!");
        }

        String filePath = request.getServletContext().getRealPath("/uploads/");
        if (! new File(filePath).exists()) {
            new File(filePath).mkdir();
        }

        String originalFileName = Objects.requireNonNull(file.getOriginalFilename())
                .replace("/", "")
                .replace("\\", "")
                .replace("\\", "")
                .replace(":", "")
                .replace("?", "")
                .replace("*", "")
                .replace("\"", "")
                .replace("|", "")
                .replace("<", "")
                .replace(">", "");

        String fileName = new Random().nextInt(10000000) + originalFileName;
        String resultFilePath = filePath + "/" + fileName;
        file.transferTo(new File(resultFilePath));

        Image createdImage = imageService.create(resultFilePath, postId);
        return ResponseEntity.ok(createdImage);
    }

    @GetMapping("/{postId}")
    @Operation(
            summary = "find and return post's images by its id",
            description = "get all images by post id"
    )
    public ResponseEntity<List<Image>> getAllByPostId(@PathVariable Long postId) {
        List<Image> images = imageService.findAllByPostId(postId);
        return ResponseEntity.ok(images);
    }
}