package shop.yesaladin.front.writing.service.inter;

import java.util.List;
import shop.yesaladin.front.writing.dto.AuthorResponseDto;

/**
 * 저자 조회 요청을 위한 service 구현체 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
public interface QueryAuthorService {

    List<AuthorResponseDto> findAll();
}
