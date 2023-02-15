package shop.yesaladin.front.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.member.dto.MemberAddressRequestDto;
import shop.yesaladin.front.member.dto.MemberAddressResponseDto;
import shop.yesaladin.front.member.service.inter.CommandMemberAddressService;

/**
 * 회원 배송지 관련 rest controller입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/address")
public class MemberAddressRestController {

    private final CommandMemberAddressService commandMemberAddressService;

    /**
     * 회원의 배송지를 등록합니다.
     *
     * @param request 배송지 정보
     * @return 배송지 등록 성공 여부
     * @author 최예린
     * @since 1.0
     */
    @PostMapping
    public MemberAddressResponseDto createMemberAddress(@RequestBody MemberAddressRequestDto request) {
        ResponseDto<MemberAddressResponseDto> response = commandMemberAddressService.createMemberAddress(
                request.toCreateRequestDto());
        return (response.isSuccess()) ? response.getData() : null;
    }

    /**
     * 회원의 배송지를 삭제합니다.
     *
     * @param addressId 삭제할 배송지 pk
     * @return 배송지 삭제 성공 여부
     * @author 최예린
     * @since 1.0
     */
    @PostMapping("/{addressId}")
    public boolean deleteMemberAddress(@PathVariable Long addressId) {
        ResponseDto<Object> response = commandMemberAddressService.deleteAddress(addressId);

        return response.isSuccess();
    }
}
