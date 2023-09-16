package com.halcyon.julioimage.service.image;

import com.halcyon.julioimage.model.Image;
import com.halcyon.julioimage.model.Post;
import com.halcyon.julioimage.model.User;
import com.halcyon.julioimage.repository.IImageRepository;
import com.halcyon.julioimage.service.auth.AuthService;
import com.halcyon.julioimage.service.post.PostService;
import com.halcyon.julioimage.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
}