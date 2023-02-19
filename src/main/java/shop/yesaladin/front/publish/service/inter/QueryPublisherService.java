package shop.yesaladin.front.publish.service.inter;

import org.springframework.data.domain.Pageable;
import shop.yesaladin.front.common.dto.PageRequestDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.publish.dto.PublishersResponseDto;

/**
 * 출판사 조회 요청을 위한 service 구현체 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
public interface QueryPublisherService {

    /**
     * 관리자용 출판사 전체 조회를 요청하여 응답받습니다.
     *
     * @param pageRequestDto Pagination을 위한 Dto
     * @return 응답받은 출판사 전체 조회 Dto
     */
    PaginatedResponseDto<PublishersResponseDto> findAllForManager(PageRequestDto pageRequestDto);

    /**
     * 출판사 이름으로 출판사를 검색하는 메서드
     *
     * @param name 출판사 이름
     * @param pageable 페이지 정보
     * @return 검색 결과
     * @author 김선홍
     * @since 1.0
     */
    PaginatedResponseDto<PublishersResponseDto> findByNameForManager(String name, Pageable pageable);
}
