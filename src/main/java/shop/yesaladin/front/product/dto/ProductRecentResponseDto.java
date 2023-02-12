package shop.yesaladin.front.product.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 최근 본 상품과 신작 작품의 ResponseDto
 *
 * @author 김선홍
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRecentResponseDto {
    private Long id;
    private String title;
    private String thumbnailFileUrl;
    private long sellingPrice;
    private int rate;
    private String publisher;
    private List<String> author;
    private Boolean isForcedOutOfStock;
    private Long quantity;
}
