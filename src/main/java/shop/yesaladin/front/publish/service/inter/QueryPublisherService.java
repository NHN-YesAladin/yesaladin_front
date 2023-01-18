package shop.yesaladin.front.publish.service.inter;

import java.util.List;
import shop.yesaladin.front.publish.dto.PublisherResponseDto;

/**
 * 출판사 조회 요청을 위한 service 구현체 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
public interface QueryPublisherService {

    List<PublisherResponseDto> findAll();
}
