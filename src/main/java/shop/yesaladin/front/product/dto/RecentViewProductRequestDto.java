package shop.yesaladin.front.product.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 최근 본 상품 요청 dto
 *
 * @author 김선홍
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecentViewProductRequestDto {
    List<Long> totalIds;
    List<Long> pageIds;
}
