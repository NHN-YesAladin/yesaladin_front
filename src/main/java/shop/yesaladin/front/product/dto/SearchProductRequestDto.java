package shop.yesaladin.front.product.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchProductRequestDto {
    String selected;
    String input;
    @Min(value = 0)
    int offset;
    @Max(value = 20)
    @Min(value = 1)
    int size;
}
