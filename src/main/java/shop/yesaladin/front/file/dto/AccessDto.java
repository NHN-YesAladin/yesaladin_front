package shop.yesaladin.front.file.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Jackson으로 Json에서 토큰을 얻기 위해 사용하는 Dto 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessDto {

    private TokenDto token;
}
