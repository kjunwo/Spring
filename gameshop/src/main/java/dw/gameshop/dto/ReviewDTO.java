package dw.gameshop.dto;

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
