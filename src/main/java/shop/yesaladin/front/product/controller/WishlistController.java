package shop.yesaladin.front.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import shop.yesaladin.front.product.dto.ProductDetailResponseDto;
import shop.yesaladin.front.product.dto.WishlistResponseDto;

@RequiredArgsConstructor
@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    private final ObjectMapper objectMapper;

    @GetMapping
    public ModelAndView myPageWishlist(HttpServletRequest request, HttpServletResponse response)
            throws JsonProcessingException {
        ModelAndView modelAndView = new ModelAndView("member/wishlist");
        modelAndView.addObject("recentList", getRecentlyViewList(checkCookie(request, response)));
        return modelAndView;
    }

    public List<WishlistResponseDto> getRecentlyViewList(Cookie cookie)
            throws JsonProcessingException {
        List<WishlistResponseDto> recentlyViewDtos = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(cookie.getValue());

        while (stringTokenizer.hasMoreTokens()) {
            recentlyViewDtos.add(WishlistResponseDto.getWishlistResponseDto(
                    objectMapper.readValue(stringTokenizer.nextToken(), ProductDetailResponseDto.class)
            ));
        }
        return recentlyViewDtos;
    }

    public Cookie checkCookie(HttpServletRequest request, HttpServletResponse response) {
        for(Cookie cookie: request.getCookies()) {
            if(cookie.getName().equals(QueryProductWebController.COOKIENAME)) {
                return cookie;
            }
        }
        Cookie cookie = new Cookie(QueryProductWebController.COOKIENAME, "");
        cookie.setMaxAge(30000);
        response.addCookie(cookie);
        return cookie;
    }
}
