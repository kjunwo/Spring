package com.dw.gameshop.dto;

import com.dw.gameshop.enums.GameRating;
import com.dw.gameshop.model.Review;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReviewDTO {
    private long reviewId;
    private String gameTitle;
    private String userName;
    private String reviewPoint;
    private String reviewText;
}
