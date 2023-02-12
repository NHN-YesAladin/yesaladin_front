package shop.yesaladin.front.order.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

/**
 * 정기구독 생성을 요청하는 dto 입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@Getter
@Setter
public class OrderSubscribeCreateRequestDto {

    @Range(min = 1, max = 31)
    private int expectedDay;
    private int intervalMonth;


}
