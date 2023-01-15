package shop.yesaladin.front.member.controller;


import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import shop.yesaladin.front.member.service.inter.SearchMemberService;

/**
 * 관리자단에서 회원을 검색해주는 컨트롤러 입니다.
 *
 * @author : 김선홍
 * @since : 1.0
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/search/members")
public class MemberSearchController {

    private final SearchMemberService searchMemberService;
    private static final String VIEW_NAME = "member/manager-search-member-list";
    private static final String MEMBERS = "members";

    /**
     * 회원으르 검색하는 뷰로 이동
     *
     * @return 회원을 관리하는 뷰
     * @author : 김선홍
     * @since : 1.0
     */
    @GetMapping
    public String goView() {
        return VIEW_NAME;
    }

    /**
     * 로그인 아이디를 이용해 회원을 검색하는 메서드
     *
     * @param loginId 검색할 회원의 로그인아이디
     * @return 검색된 회원의 정보와 회원을 관리하는 view
     * @author : 김선홍
     * @since : 1.0
     */
    @GetMapping("/loginid/{loginid}")
    public ModelAndView searchByLoginId(@PathVariable(name = "loginid") String loginId) {
        ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
        modelAndView.addObject(MEMBERS, searchMemberService.searchByLoginId(loginId));
        return modelAndView;
    }

    /**
     * 닉네임으로 회원을 검색하는 메서드
     *
     * @param nickname 검색할 회원의 닉네임
     * @return 검색된 회원의 정보와 회원을 관리하는 view
     * @author : 김선홍
     * @since : 1.0
     */
    @GetMapping("/nickname/{nickname}")
    public ModelAndView searchByNickname(@PathVariable String nickname) {
        ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
        modelAndView.addObject(MEMBERS, searchMemberService.searchByNickname(nickname));
        return modelAndView;
    }

    /**
     * 핸드폰 번호로 회원을 검색하는 메서드
     *
     * @param phone 검색할 회원의 핸드폰 번호
     * @return 검색된 회원의 정보와 회원을 관리하는 view
     * @author : 김선홍
     * @since : 1.0
     */
    @GetMapping("/phone/{phone}")
    public ModelAndView searchByPhone(@PathVariable String phone) {
        ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
        modelAndView.addObject(MEMBERS, searchMemberService.searchByPhone(phone));
        return modelAndView;
    }

    /**
     * 이름으로 회원을 검색하는 메서드
     *
     * @param name 검색할 회원의 이름
     * @return 검색된 회원의 정보 리스트와 회원을 관리하는 view
     * @author : 김선홍
     * @since : 1.0
     */
    @GetMapping("/name/{name}")
    public ModelAndView searchByName(@PathVariable String name) {
        ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
        modelAndView.addObject(MEMBERS, searchMemberService.searchByName(name));
        return modelAndView;
    }

    /**
     * 회원 가입날로 회원을 검색하는 메서드
     *
     * @param signUpDate 검색할 회원의 회원가입날
     * @return 검색된 회원의 정보 리스트와 회원을 관리하는 view
     * @author : 김선홍
     * @since : 1.0
     */
    @GetMapping("/signupdate/{signupdate}")
    public ModelAndView searchBySignUpDate(@PathVariable(name = "signupdate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate signUpDate) {
        ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
        modelAndView.addObject(MEMBERS, searchMemberService.searchBySignUpDate(signUpDate));
        return modelAndView;
    }
}
