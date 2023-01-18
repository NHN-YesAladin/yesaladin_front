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

    List<TagResponseDto> findAll();
}
