package shop.yesaladin.front.wishlist.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 위시리스트 등록 결과 Dto
 *
 * @author 김선홍
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WishlistSaveResponseDto {
    private Long productId;
    private LocalDateTime registeredDateTime;
}
