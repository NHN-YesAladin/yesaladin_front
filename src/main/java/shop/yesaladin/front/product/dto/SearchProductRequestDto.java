package shop.yesaladin.front.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchProductRequestDto {
    private String selected;
    private String input;
}
