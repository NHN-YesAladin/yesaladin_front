package shop.yesaladin.front.writing.service.inter;

import shop.yesaladin.front.writing.dto.AuthorResponseDto;

import java.util.List;

/**
 * 저자 조회 요청을 위한 service 구현체 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
public interface QueryAuthorService {

    /**
     * 저자 전체 조회를 요청하여 응답을 받습니다.
     *
     * @return 응답받은 전체 조회된 Dto list
     * @author 이수정
     * @since 1.0
     */
    List<AuthorResponseDto> findAll();
}
