package shop.yesaladin.front.member.service.inter;

import java.util.List;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.member.dto.MemberAddressResponseDto;

/**
 * 회원 배송지 조회 관련 service interface 입니다.
 *
 * @author 최예린
 * @since 1.0
 */

public interface QueryMemberAddressService {

    /**
     * 회원의 배송지 목록을 조회합니다.
     *
     * @return 회원의 배송지 목록
     * @author 최예린
     * @since 1.0
     */
    ResponseDto<List<MemberAddressResponseDto>> getMemberAddresses();
}
