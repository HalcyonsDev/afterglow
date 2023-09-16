package com.halcyon.julioimage.service.comment;

import com.halcyon.julioimage.dto.comment.NewCommentDto;
import com.halcyon.julioimage.model.Comment;
import com.halcyon.julioimage.model.Post;
import com.halcyon.julioimage.model.User;
import com.halcyon.julioimage.repository.ICommentRepository;
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
public class CommentService {
    private final ICommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;
    private final AuthService authService;

    public Comment create(NewCommentDto dto) {
        Post post = postService.findById(dto.getPostId());
        User owner = userService.findByEmail(authService.getAuthInfo().getEmail());

        Comment comment = new Comment(dto.getValue());
        comment.setPost(post);
        comment.setOwner(owner);

        return commentRepository.save(comment);
    }

    public String deleteById(Long id) {
        User user = userService.findByEmail(authService.getAuthInfo().getEmail());
        Comment comment = findById(id);

        if (!comment.getOwner().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this comment.");
        }

        commentRepository.deleteById(id);
        return "Comment deleted successfully.";
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment with this id not found."));
    }

    public List<Comment> findAllByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId);
    }

    public Comment updateValueById(Long id, String value) {
        Comment comment = findById(id);
        User user = userService.findByEmail(authService.getAuthInfo().getEmail());

        if (!comment.getOwner().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this comment.");
        }

        commentRepository.updateValueById(value, id);
        comment.setValue(value);

        return comment;
    }
}