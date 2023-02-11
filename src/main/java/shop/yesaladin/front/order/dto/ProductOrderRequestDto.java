package shop.yesaladin.front.order.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 주문한 상품의 정보를 보내기 위한 dto 클래스입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderRequestDto {

    @NotBlank
    private String isbn;
    @Min(1)
    private int quantity;

    @Override
    public String toString() {
        return isbn + '/' + quantity;
    }
}
