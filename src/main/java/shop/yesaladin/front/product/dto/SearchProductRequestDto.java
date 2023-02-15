package shop.yesaladin.front.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@AllArgsConstructor
public class SearchProductRequestDto {
    String selected;
    String input;
}
