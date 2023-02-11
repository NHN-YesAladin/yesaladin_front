package shop.yesaladin.front.order.dto;

import org.springframework.core.convert.converter.Converter;

/**
 * 주문서의 주문상품 데이터를 String 에서 dto 클래스로 변환합니다.
 *
 * @author 최예린
 * @since 1.0
 */
public class StringToProductOrderRequestDto implements Converter<String, ProductOrderRequestDto> {

    @Override
    public ProductOrderRequestDto convert(String source) {
        String[] s = source.replaceAll("[\\[\\]]", "").split("/");
        return new ProductOrderRequestDto(s[0], Integer.parseInt(s[1]));
    }
}
