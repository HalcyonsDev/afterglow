package com.halcyon.afterglow.service.rating;

import com.halcyon.afterglow.dto.rating.NewRatingDto;
import com.halcyon.afterglow.model.Post;
import com.halcyon.afterglow.model.Rating;
import com.halcyon.afterglow.model.RatingType;
import com.halcyon.afterglow.model.User;
import com.halcyon.afterglow.repository.RatingRepository;
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
public class RatingService {
    private final RatingRepository ratingRepository;
    private final UserService userService;
    private final AuthService authService;
    private final PostService postService;

    public Rating create(NewRatingDto dto) {
        User user = userService.findByEmail(authService.getAuthInfo().getEmail());
        Post post = postService.findById(dto.getPostId());

        if (ratingRepository.existsByOwnerAndPost(user, post)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rating for this post has already been created.");
        }

        Rating rating = new Rating(dto.getType(), user, post);
        return ratingRepository.save(rating);
    }

    public String deleteById(Long ratingId) {
        User user = userService.findByEmail(authService.getAuthInfo().getEmail());
        Rating rating = findById(ratingId);

        if (!rating.getOwner().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this rating.");
        }

        ratingRepository.delete(rating);
        return "Rating deleted successfully.";
    }

    public Rating findById(Long ratingId) {
        return ratingRepository.findById(ratingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rating with this id not found."));
    }

    public List<Rating> findLikesByPostId(Long postId) {
        return ratingRepository.findAllByPostIdAndType(postId, RatingType.LIKE);
    }

    public List<Rating> findDislikesByPostId(Long postId) {
        return ratingRepository.findAllByPostIdAndType(postId, RatingType.DISLIKE);
    }
}
