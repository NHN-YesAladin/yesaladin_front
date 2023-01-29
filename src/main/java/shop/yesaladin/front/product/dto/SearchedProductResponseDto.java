package shop.yesaladin.front.product.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchedProductResponseDto {
    List<SearchedProductDto> products;
    Long count;
}
