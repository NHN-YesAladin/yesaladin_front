package shop.yesaladin.front.member.controller.web;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import shop.yesaladin.front.member.dto.MemberManagerListResponseDto;
import shop.yesaladin.front.member.service.inter.QueryMemberService;

/**
 * 관리자가 회원의 정보를 관리하는 컨트롤러
 *
 * @author 김선홍
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/product/member")
public class MemberManagerWebController {

    private final QueryMemberService queryMemberService;
    private static final String VIEW = "manager/member/manager-member-manage";

    /**
     *
     * @return
     */
    @GetMapping
    public String goView() {
        return VIEW;
    }

    /**
     * 회원의 loginId로 회원의 정보를 조회하는 메서드
     *
     * @param loginId 회원의 loginId
     * @return 조회된 회원의 정보와 뷰 이름
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping(params = "loginid")
    public ModelAndView manageMemberInfoByLoginId(@RequestParam(name = "loginid") String loginId) {
        log.info("loginId: " + loginId);
        ModelAndView modelAndView = new ModelAndView(VIEW);
        modelAndView.addObject(
                "memberList",
                List.of(queryMemberService.manageMemberInfoByLoginId(loginId))
        );
        modelAndView.addObject("count", 1);
        return modelAndView;
    }

    /**
     * 회원의 nickname 으로 회원의 정보를 조회하는 메서드
     *
     * @param nickname 회원의 nickname
     * @return 조회된 회원의 정보와 뷰 이름
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping(params = "nickname")
    public ModelAndView manageMemberInfoByNickname(@RequestParam(name = "nickname") String nickname) {
        ModelAndView modelAndView = new ModelAndView(VIEW);
        modelAndView.addObject(
                "memberList",
                List.of(queryMemberService.manageMemberInfoByNickname(nickname))
        );
        modelAndView.addObject("count", 1);
        return modelAndView;
    }

    /**
     * 회원의 phone 으로 회원의 정보를 조회하는 메서드
     *
     * @param phone 회원의 phone
     * @return 조회된 회원의 정보와 뷰 이름
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping(params = "phone")
    public ModelAndView manageMemberInfoByPhone(@RequestParam(name = "phone") String phone) {
        ModelAndView modelAndView = new ModelAndView(VIEW);
        modelAndView.addObject(
                "memberList",
                List.of(queryMemberService.manageMemberInfoByPhone(phone))
        );
        modelAndView.addObject("count", 1);
        return modelAndView;
    }

    /**
     * 회원의 name 으로 회원의 정보를 조회하는 메서드
     *
     * @param name 회원의 name
     * @return 조회된 회원의 정보 리스트와 뷰 이름
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping(params = "name")
    public ModelAndView manageMemberInfoByName(
            @RequestParam(name = "name") String name,
            int page,
            int size
    ) {
        ModelAndView modelAndView = new ModelAndView(VIEW);
        MemberManagerListResponseDto managerListResponseDto = queryMemberService.manageMemberInfoByName(
                name,
                page,
                size
        );
        modelAndView.addObject(
                "memberList",
                managerListResponseDto.getMemberManagerResponseDtoList()
        );
        modelAndView.addObject("count", managerListResponseDto.getCount());
        return modelAndView;
    }

    /**
     * 회원의 loginId로 회원의 정보를 조회하는 메서드
     *
     * @param signUpDate 회원의 signUpDate
     * @return 조회된 회원의 정보 리스트와 뷰 이름
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping(params = "signupdate")
    public ModelAndView manageMemberInfoBySignUpDate(
            @RequestParam(name = "signupdate") LocalDate signUpDate,
            int page,
            int size
    ) {
        ModelAndView modelAndView = new ModelAndView(VIEW);
        MemberManagerListResponseDto managerListResponseDto = queryMemberService.manageMemberInfoBySignUpDate(
                signUpDate,
                page,
                size
        );
        modelAndView.addObject("memberList",
                managerListResponseDto.getMemberManagerResponseDtoList());
        modelAndView.addObject("count", managerListResponseDto.getCount());
        return modelAndView;
    }
}
