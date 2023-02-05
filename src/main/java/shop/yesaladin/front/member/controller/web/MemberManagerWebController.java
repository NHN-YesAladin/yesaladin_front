package shop.yesaladin.front.member.controller.web;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/manage/member")
public class MemberManagerWebController {

    private final QueryMemberService queryMemberService;
    private static final String VIEW = "manager/member/manager-member-manage";
    private static final String URL = "/manage/member";
    private static final String DATE_PARAM = "&signupdate=";
    private static final String NAME_PARAM = "&name=";
    private static final int SIZE = 5;

    /**
     * 관리자의 회원 관리 뷰로 이동
     *
     * @return 관리자의 회원 관리 뷰
     * @author 김선홍
     * @since 1.0
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
            @RequestParam(defaultValue = "0") int page
    ) {
        ModelAndView modelAndView = new ModelAndView(VIEW);
        MemberManagerListResponseDto managerListResponseDto = queryMemberService.manageMemberInfoByName(
                name,
                page * SIZE,
                SIZE
        );
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject(
                "memberList",
                managerListResponseDto.getMemberManagerResponseDtoList()
        );
        modelAndView.addObject("totalPage", managerListResponseDto.getCount()/5);
        modelAndView.addObject("url", URL);
        modelAndView.addObject("queryParam", NAME_PARAM+name);
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
            @RequestParam(name = "signupdate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate signUpDate,
            @RequestParam(defaultValue = "0")int page
    ) {
        ModelAndView modelAndView = new ModelAndView(VIEW);
        modelAndView.addObject("currentPage", page);
        MemberManagerListResponseDto managerListResponseDto = queryMemberService.manageMemberInfoBySignUpDate(
                signUpDate,
                page * SIZE,
                SIZE
        );
        modelAndView.addObject("memberList",
                managerListResponseDto.getMemberManagerResponseDtoList());
        modelAndView.addObject("totalPage", managerListResponseDto.getCount()/5+1);
        modelAndView.addObject("url", URL);
        modelAndView.addObject("queryParam", DATE_PARAM +signUpDate);
        return modelAndView;
    }
}
