package shop.yesaladin.front.tag.service.inter;

import shop.yesaladin.front.common.dto.PageRequestDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.tag.dto.TagResponseDto;
import shop.yesaladin.front.tag.dto.TagsResponseDto;

import java.util.List;

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

    /**
     * 관리자용 태그 전체 조회를 요청하여 응답받습니다.
     *
     * @param pageRequestDto Pagination을 위한 Dto
     * @return 응답받은 태그 전체 조회 Dto
     */
    PaginatedResponseDto<TagsResponseDto> findAllForManager(PageRequestDto pageRequestDto);
}
