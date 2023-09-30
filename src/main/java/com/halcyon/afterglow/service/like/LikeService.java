package com.halcyon.afterglow.service.like;

import com.halcyon.afterglow.dto.like.NewLikeDto;
import com.halcyon.afterglow.model.Like;
import com.halcyon.afterglow.model.Post;
import com.halcyon.afterglow.model.User;
import com.halcyon.afterglow.repository.ILikeRepository;
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
public class LikeService {
    private final ILikeRepository likeRepository;
    private final PostService postService;
    private final UserService userService;
    private final AuthService authService;

    public Like create(NewLikeDto dto) {
        User owner = userService.findByEmail(authService.getAuthInfo().getEmail());
        Post post = postService.findById(dto.getPostId());

        if (likeRepository.existsByOwnerAndPost(owner, post)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Like for this post has already been created.");
        }

        Like like = new Like();
        like.setPost(post);
        like.setOwner(owner);

        return likeRepository.save(like);
    }

    public String deleteById(Long id) {
        User user = userService.findByEmail(authService.getAuthInfo().getEmail());
        Like like = findById(id);

        if (!like.getOwner().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this like.");
        }

        likeRepository.deleteById(id);
        return "Like deleted successfully.";
    }

    public Like findById(Long id) {
        return likeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Like with this id not found."));
    }

    public List<Like> findAllByPostId(Long postId) {
        return likeRepository.findAllByPostId(postId);
    }
}