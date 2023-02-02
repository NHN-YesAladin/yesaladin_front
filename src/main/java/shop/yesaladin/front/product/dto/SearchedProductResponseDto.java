package shop.yesaladin.front.product.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchedProductResponseDto {
    List<SearchedProductDto> products;
    Long count;
}
