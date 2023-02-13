package shop.yesaladin.front.wishlist.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 위시리스트 response DTO
 *
 * @author 김선홍
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WishlistResponseDto {
    private Long id;
    private String title;
    private String thumbnailFileUrl;
    private long sellingPrice;
    private int rate;
    private String publisher;
    private List<String> author;
    private Boolean isForcedOutOfStock;
    private Long quantity;
    private LocalDateTime registeredDateTime;
}
