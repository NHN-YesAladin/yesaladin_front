package shop.yesaladin.front.publish.service.inter;

import shop.yesaladin.front.common.dto.PageRequestDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.publish.dto.PublisherResponseDto;
import shop.yesaladin.front.publish.dto.PublishersResponseDto;

import java.util.List;

/**
 * 출판사 조회 요청을 위한 service 구현체 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
public interface QueryPublisherService {

    /**
     * 출판사 조회를 받아와 저장한 Dto 입니다.
     *
     * @author 이수정
     * @since 1.0
     */
    List<PublisherResponseDto> findAll();

    /**
     * 관리자용 출판사 전체 조회를 요청하여 응답받습니다.
     *
     * @param pageRequestDto Pagination을 위한 Dto
     * @return 응답받은 출판사 전체 조회 Dto
     */
    PaginatedResponseDto<PublishersResponseDto> findAllForManager(PageRequestDto pageRequestDto);
}
