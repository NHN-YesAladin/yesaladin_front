package shop.yesaladin.front.member.service.inter;

import java.time.LocalDate;
import org.springframework.data.domain.Pageable;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.member.dto.MemberGradeHistoryResponseDto;

/**
 * 회원 등급변경 내역을 조회하는 서비스입니다.
 *
 * @author 최예린
 * @since 1.0
 */
public interface QueryMemberGradeHistoryService {

    /**
     * 회원 등급 변경 내역을 조회합니다.
     *
     * @param pageable  페이지와 사이즈
     * @param startDate 조회 시작일
     * @param endDate   조회 끝일
     * @return 기간별 페이징된 회원 등급 변경 내역 데이터
     * @author 최예린
     * @since 1.0
     */
    PaginatedResponseDto<MemberGradeHistoryResponseDto> getMemberGradeHsitoryHistories(
            Pageable pageable,
            LocalDate startDate,
            LocalDate endDate
    );
}
