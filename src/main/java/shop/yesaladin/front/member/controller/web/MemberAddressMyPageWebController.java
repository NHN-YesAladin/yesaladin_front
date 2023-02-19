package shop.yesaladin.front.member.controller.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.yesaladin.front.member.dto.MemberAddressRequestDto;
import shop.yesaladin.front.member.dto.MemberAddressResponseDto;
import shop.yesaladin.front.member.service.inter.CommandMemberAddressService;
import shop.yesaladin.front.member.service.inter.QueryMemberAddressService;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/mypage/address")
public class MemberAddressMyPageWebController {

    private final QueryMemberAddressService memberAddressQueryService;
    private final CommandMemberAddressService memberAddressCommandService;

    /**
     * 회원의 배송지 목록 페이지를 반환합니다.
     *
     * @param model 모델
     * @return 회원의 배송지 목록 페이지
     * @author 최예린
     * @since 1.0
     */
    @GetMapping
    public String address(Model model) {
        List<MemberAddressResponseDto> addresses = memberAddressQueryService.getMemberAddresses()
                .getData();

        model.addAttribute("addresses", addresses);

        return "mypage/member/address";
    }

    /**
     * 대표 배송지를 설정합니다.
     *
     * @param addressId 대표 배송지 pk
     * @return 회원의 배송지 목록 페이지
     * @author 최예린
     * @since 1.0
     */
    @GetMapping("/default")
    public String addressMarkAsDefault(@RequestParam Long addressId) {
        memberAddressCommandService.updateDefaultAddress(addressId);

        return "redirect:/mypage/address";
    }

    /**
     * 배송지를 삭제합니다.
     *
     * @param addressId 대표 배송지 pk
     * @return 회원의 배송지 목록 페이지
     * @author 최예린
     * @since 1.0
     */
    @GetMapping("/delete")
    public String deleteAddress(@RequestParam Long addressId) {
        memberAddressCommandService.deleteAddress(addressId);

        return "redirect:/mypage/address";
    }
}
