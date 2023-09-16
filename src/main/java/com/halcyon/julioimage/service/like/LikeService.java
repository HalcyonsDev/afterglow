package com.halcyon.julioimage.service.like;

import com.halcyon.julioimage.dto.like.NewLikeDto;
import com.halcyon.julioimage.model.Like;
import com.halcyon.julioimage.model.Post;
import com.halcyon.julioimage.model.User;
import com.halcyon.julioimage.repository.ILikeRepository;
import com.halcyon.julioimage.service.auth.AuthService;
import com.halcyon.julioimage.service.post.PostService;
import com.halcyon.julioimage.service.user.UserService;
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
        User user = userService.findByEmail(authService.getAuthInfo().getEmail());
        Post post = postService.findById(dto.getPostId());

        Like like = new Like();
        like.setPost(post);
        like.setOwner(user);

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Like with this is not found."));
    }

    public List<Like> findAllByPostId(Long postId) {
        return likeRepository.findAllByPostId(postId);
    }
}