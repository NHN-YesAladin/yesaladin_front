package shop.yesaladin.front.member.controller.web;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.member.dto.MemberManagerResponseDto;
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
    private static final String VIEW = "manager/member/manager-management";
    private static final String URL = "/manage/member";
    private static final String LOGIN_PARAM = "&loginid=";
    private static final String PHONE_PARAM = "&phone=";
    private static final String NICKNAME_PARAM = "&nickname=";
    private static final String DATE_PARAM = "&signupdate=";
    private static final String NAME_PARAM = "&name=";

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
     * @param pageable 페이징 정보
     * @return 조회된 회원의 정보와 뷰 이름
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping(params = "loginid")
    public ModelAndView manageMemberInfoByLoginId(@RequestParam(name = "loginid") String loginId, @PageableDefault Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView(VIEW);
        PaginatedResponseDto<MemberManagerResponseDto> result = queryMemberService.manageMemberInfoByLoginId(loginId, pageable);
        modelAndView.addObject("memberList", result.getDataList());
        modelAndView.addObject("currentPage", result.getCurrentPage());
        modelAndView.addObject("totalPage", result.getTotalPage());
        modelAndView.addObject("queryParam", LOGIN_PARAM + loginId);
        modelAndView.addObject("url", URL);
        return modelAndView;
    }

    /**
     * 회원의 nickname 으로 회원의 정보를 조회하는 메서드
     *
     * @param nickname 회원의 nickname
     * @param pageable 페이징 정보
     * @return 조회된 회원의 정보와 뷰 이름
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping(params = "nickname")
    public ModelAndView manageMemberInfoByNickname(@RequestParam(name = "nickname") String nickname, @PageableDefault Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView(VIEW);

        PaginatedResponseDto<MemberManagerResponseDto> result = queryMemberService.manageMemberInfoByNickname(nickname, pageable);
        modelAndView.addObject("memberList", result.getDataList());
        modelAndView.addObject("currentPage", result.getCurrentPage());
        modelAndView.addObject("totalPage", result.getTotalPage());
        modelAndView.addObject("queryParam", NICKNAME_PARAM + nickname);
        modelAndView.addObject("url", URL);
        return modelAndView;
    }

    /**
     * 회원의 phone 으로 회원의 정보를 조회하는 메서드
     *
     * @param phone 회원의 phone
     * @param pageable 페이징 정보
     * @return 조회된 회원의 정보와 뷰 이름
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping(params = "phone")
    public ModelAndView manageMemberInfoByPhone(@RequestParam(name = "phone") String phone, @PageableDefault Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView(VIEW);
        PaginatedResponseDto<MemberManagerResponseDto> result = queryMemberService.manageMemberInfoByPhone(phone, pageable);
        modelAndView.addObject("memberList", result.getDataList());
        modelAndView.addObject("currentPage", result.getCurrentPage());
        modelAndView.addObject("totalPage", result.getTotalPage());
        modelAndView.addObject("queryParam", PHONE_PARAM+phone);
        modelAndView.addObject("url", URL);
        return  modelAndView;
    }

    /**
     * 회원의 name 으로 회원의 정보를 조회하는 메서드
     *
     * @param name 회원의 name
     * @param pageable 페이징 정보
     * @return 조회된 회원의 정보 리스트와 뷰 이름
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping(params = "name")
    public ModelAndView manageMemberInfoByName(
            @RequestParam(name = "name") String name,
            @PageableDefault Pageable pageable
    ) {
        ModelAndView modelAndView = new ModelAndView(VIEW);
        PaginatedResponseDto<MemberManagerResponseDto> result = queryMemberService.manageMemberInfoByName(name, pageable);
        modelAndView.addObject("memberList", result.getDataList());
        modelAndView.addObject("currentPage", result.getCurrentPage());
        modelAndView.addObject("totalPage", result.getTotalPage());
        modelAndView.addObject("queryParam", NAME_PARAM + name);
        modelAndView.addObject("url", URL);

        return  modelAndView;
    }

    /**
     * 회원의 loginId로 회원의 정보를 조회하는 메서드
     *
     * @param signUpDate 회원의 signUpDate
     * @param pageable 페이징 정보
     * @return 조회된 회원의 정보 리스트와 뷰 이름
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping(params = "signupdate")
    public ModelAndView manageMemberInfoBySignUpDate(
            @RequestParam(name = "signupdate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate signUpDate,
            @PageableDefault Pageable pageable
    ) {
        ModelAndView modelAndView = new ModelAndView(VIEW);
        PaginatedResponseDto<MemberManagerResponseDto> result = queryMemberService.manageMemberInfoBySignUpDate(signUpDate, pageable);
        modelAndView.addObject("memberList", result.getDataList());
        modelAndView.addObject("currentPage", result.getCurrentPage());
        modelAndView.addObject("totalPage", result.getTotalPage());
        modelAndView.addObject("queryParam", DATE_PARAM+ signUpDate);
        modelAndView.addObject("url", URL);
        return modelAndView;
    }
}
