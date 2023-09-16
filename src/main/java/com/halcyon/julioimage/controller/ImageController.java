package com.halcyon.julioimage.controller;

import com.halcyon.julioimage.model.Image;
import com.halcyon.julioimage.service.image.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {
    private final HttpServletRequest request;
    private final ImageService imageService;

    @PostMapping("/upload/{postId}")
    public ResponseEntity<Image> create(@PathVariable Long postId, @RequestParam("file")MultipartFile file) throws IOException {
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
}