package com.halcyon.afterglow.service.image;

import com.halcyon.afterglow.model.Image;
import com.halcyon.afterglow.model.Post;
import com.halcyon.afterglow.model.User;
import com.halcyon.afterglow.repository.IImageRepository;
import com.halcyon.afterglow.service.auth.AuthService;
import com.halcyon.afterglow.service.post.PostService;
import com.halcyon.afterglow.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final IImageRepository imageRepository;
    private final PostService postService;
    private final UserService userService;
    private final AuthService authService;

    public Image create(String source, Long postId) {
        Post post = postService.findById(postId);
        User user = userService.findByEmail(authService.getAuthInfo().getEmail());

        if (!post.getOwner().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this post.");
        }

        Image image = new Image(source);
        image.setPost(post);

        return imageRepository.save(image);
    }

    public List<Image> findAllByPostId(Long postId) {
        User user = userService.findByEmail(authService.getAuthInfo().getEmail());
        Post post = postService.findById(postId);

        if (!post.getOwner().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this post.");
        }

        return imageRepository.findImagesByPost(post);
    }
}