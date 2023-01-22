package shop.yesaladin.front.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import shop.yesaladin.front.product.dto.ProductDetailResponseDto;
import shop.yesaladin.front.product.dto.WishlistResponseDto;

/**
 * 마이페이지의 위시리스트 뷰, 삭제 컨트롤러 입니다.
 *
 * @author : 김선홍
 * @since : 1.0
 */
@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/wishlist")
public class WishlistController {

    private final ObjectMapper objectMapper;

    /**
     * GET 마이페이지로 가는 메서드 입니다.
     *
     * @param request  쿠키를 얻기위한 HttpServletRequest 객체
     * @param response 만약 쿠키가 없는 상태라면 생성한 쿠키를 저장할 HttpServletResponse 객체
     * @return 위시리스트 뷰로 이동
     * @throws JsonProcessingException 파싱이 잘못될 경우 Excepiton
     */
    @GetMapping
    public ModelAndView myPageWishlist(HttpServletRequest request, HttpServletResponse response)
            throws JsonProcessingException {
        ModelAndView modelAndView = new ModelAndView("member/wishlist");
        List<WishlistResponseDto> recentList = objectMapper.readValue(
                createCookie(request, response).getValue(), new TypeReference<List<WishlistResponseDto>>() {}
        );
        modelAndView.addObject("recentList", recentList);
        return modelAndView;
    }

    /**
     * 최근 본 상품을 삭제하는 메서드
     *
     * @param id       삭제할 최근 본 상품의 리스트 번호
     * @param request  쿠키를 얻기위한 HttpServletRequest 객체
     * @param response 수정한 쿠키를 담기 위한 HttpServletResponse 객체
     * @return 마이페이지 위시리스트 뷰로 이동
     * @throws JsonProcessingException 파싱 Exception
     * @author : 김선홍
     * @since : 1.0
     */
    @DeleteMapping
    @RequestMapping("/recent/{id}")
    public ModelAndView recentListDelete(
            @PathVariable int id,
            HttpServletRequest request,
            HttpServletResponse response
    )
            throws JsonProcessingException {
        if (checkCookie(request)) {

        }
        Cookie recentViewCookie = createCookie(request, response);

        List<WishlistResponseDto> recentlyViewDtos = objectMapper.readValue(
                recentViewCookie.getValue(), new TypeReference<List<WishlistResponseDto>>() {}
        );

        recentlyViewDtos.remove(id);

        recentViewCookie.setValue(objectMapper.writeValueAsString(recentlyViewDtos));
        response.addCookie(recentViewCookie);
        ModelAndView modelAndView = new ModelAndView("member/wishlist");
        modelAndView.addObject("recentList", recentlyViewDtos);

        return modelAndView;
    }

    /**
     * 쿠키를 가지고 있는지 확인하는 메서드
     *
     * @param request 쿠키를 얻기위한 HttpServletRequest 객체
     * @return 쿠키가 있다면 true, 없다면 false
     * @author : 김선홍
     * @since : 1.0
     */
    public boolean checkCookie(HttpServletRequest request) {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(QueryProductWebController.COOKIENAME)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 위시리스트에서 최근 본 상품이 담긴 쿠키를 구하기 위한 메서드 만약 쿠키가 없다면 생성한다.
     *
     * @param request  쿠키를 얻기위한 HttpServletRequest 객체
     * @param response 만약 쿠키가 없는 상태라면 생성한 쿠키를 저장할 HttpServletResponse 객체
     * @return 찾은 혹은 생성한 쿠키
     * @author : 김선홍
     * @since : 1.0
     */
    public Cookie createCookie(HttpServletRequest request, HttpServletResponse response)
            throws JsonProcessingException {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(QueryProductWebController.COOKIENAME)) {
                return cookie;
            }
        }
        Cookie cookie = new Cookie(
                QueryProductWebController.COOKIENAME,
                objectMapper.writeValueAsString(new ArrayList<WishlistResponseDto>())
        );
        cookie.setMaxAge(30000);
        response.addCookie(cookie);
        return cookie;
    }
}
