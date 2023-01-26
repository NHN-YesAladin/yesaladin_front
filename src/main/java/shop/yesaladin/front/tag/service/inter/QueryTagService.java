package shop.yesaladin.front.tag.service.inter;

import java.util.List;
import shop.yesaladin.front.tag.dto.TagResponseDto;

/**
 * 태그 조회 요청을 위한 service 구현체 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
public interface QueryTagService {

    /**
     * 태그 전체 조회를 요청하여 응답을 받습니다.
     *
     * @return 응답받은 전체 조회된 Dto list
     * @author 이수정
     * @since 1.0
     */
    List<TagResponseDto> findAll();
}
