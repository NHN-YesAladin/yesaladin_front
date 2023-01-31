package shop.yesaladin.front.member.service.inter;

import shop.yesaladin.front.member.dto.MemberAddressCreateRequestDto;

/**
 * 회원 배송지 관련 등록, 수정, 삭제 service interface 입니다.
 *
 * @author 최예린
 * @since 1.0
 */
public interface CommandMemberAddressService {

    /**
     * 회원의 배송지를 등록합니다.
     *
     * @param request 회원 배송지 정보
     * @author 최예린
     * @since 1.0
     */
    void createMemberAddress(MemberAddressCreateRequestDto request);

    /**
     * 해당 배송지를 대표 배송지로 설정합니다.
     *
     * @param addressId 배송지 pk
     * @author 최예린
     * @since 1.0
     */
    void updateDefaultAddress(Long addressId);

    /**
     * 회원의 배송지를 삭제합니다.
     *
     * @param addressId 삭제할 배송지 pk
     * @author 최예린
     * @since 1.0
     */
    void deleteAddress(Long addressId);
}
