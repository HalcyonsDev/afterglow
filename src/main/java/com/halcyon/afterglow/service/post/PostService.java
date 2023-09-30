package com.halcyon.afterglow.service.post;

import com.halcyon.afterglow.dto.post.NewPostDto;
import com.halcyon.afterglow.model.Post;
import com.halcyon.afterglow.model.User;
import com.halcyon.afterglow.repository.IPostRepository;
import com.halcyon.afterglow.service.auth.AuthService;
import com.halcyon.afterglow.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final IPostRepository postRepository;
    private final UserService userService;
    private final AuthService authService;

    public Post create(NewPostDto dto) {
        User owner = userService.findByEmail(authService.getAuthInfo().getEmail());

        Post post = new Post(dto.getName(), dto.getDescription());
        post.setOwner(owner);

        return postRepository.save(post);
    }

    public String deleteById(Long id) {
        User user = userService.findByEmail(authService.getAuthInfo().getEmail());
        Post post = findById(id);

        if (!post.getOwner().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this post.");
        }

        postRepository.delete(post);

        return "Post deleted successfully.";
    }

    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with this id not found."));
    }

    public List<Post> findAllByOwner() {
        User owner = userService.findByEmail(authService.getAuthInfo().getEmail());
        return postRepository.findPostsByOwner(owner);
    }

    public Post updateNameById(Long id, String name) {
        User user = userService.findByEmail(authService.getAuthInfo().getEmail());
        Post post = findById(id);

        if (!post.getOwner().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this post.");
        }

        postRepository.updateNameById(name, id);
        post.setName(name);

        return post;
    }

    public Post updateDescriptionById(Long id, String description) {
        User user = userService.findByEmail(authService.getAuthInfo().getEmail());
        Post post = findById(id);

        if (!post.getOwner().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this post.");
        }

        postRepository.updateDescriptionById(description, id);
        post.setDescription(description);

        return post;
    }
}