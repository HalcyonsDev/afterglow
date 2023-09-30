package com.halcyon.afterglow.dto.comment;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewCommentDto {
    private Long postId;

    @Size(min = 1, max = 100, message = "Value must be more than 1 character and less than 100 characters.")
    private String value;
}