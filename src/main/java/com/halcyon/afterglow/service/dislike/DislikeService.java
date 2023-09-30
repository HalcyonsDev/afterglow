package com.halcyon.afterglow.service.dislike;

import com.halcyon.afterglow.dto.dislike.NewDislikeDto;
import com.halcyon.afterglow.model.Dislike;
import com.halcyon.afterglow.model.Post;
import com.halcyon.afterglow.model.User;
import com.halcyon.afterglow.repository.IDislikeRepository;
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
public class DislikeService {
    private final IDislikeRepository dislikeRepository;
    private final PostService postService;
    private final UserService userService;
    private final AuthService authService;

    public Dislike create(NewDislikeDto dto) {
        User owner = userService.findByEmail(authService.getAuthInfo().getEmail());
        Post post = postService.findById(dto.getPostId());

        if (dislikeRepository.existsByOwnerAndPost(owner, post)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dislike for this post has already been created.");
        }

        Dislike dislike = new Dislike();
        dislike.setOwner(owner);
        dislike.setPost(post);

        return dislikeRepository.save(dislike);
    }

    public String deleteById(Long id) {
        User user = userService.findByEmail(authService.getAuthInfo().getEmail());
        Dislike dislike = findById(id);

        if (!dislike.getOwner().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this dislike.");
        }

        dislikeRepository.deleteById(id);
        return "Dislike deleted successfully.";
    }

    public Dislike findById(Long id) {
        return dislikeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dislike with this id not found."));
    }

    public List<Dislike> findAllByPostId(Long postId) {
        return dislikeRepository.findAllByPostId(postId);
    }
}