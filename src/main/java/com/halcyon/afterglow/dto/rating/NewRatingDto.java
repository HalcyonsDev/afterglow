package com.halcyon.afterglow.dto.rating;

import com.halcyon.afterglow.model.RatingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewRatingDto {
    private Long postId;
    private RatingType type;
}
