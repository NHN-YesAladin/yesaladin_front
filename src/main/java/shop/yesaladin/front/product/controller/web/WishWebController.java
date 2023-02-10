package shop.yesaladin.front.product.controller.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/wishlist")
public class WishWebController {

    private final ObjectMapper objectMapper;

    @GetMapping
    public ModelAndView memberWishList(HttpServletRequest request, HttpServletResponse response)
            throws JsonProcessingException {
        ModelAndView modelAndView = new ModelAndView("mypage/product/wishList");
        Cookie cookie1 = null;
        for(Cookie cookie: request.getCookies()) {
            if(cookie.getName().equals("recent")) {
                cookie1 = cookie;
                break;
            }
        }
        ArrayList<Long> set = new ArrayList<>(objectMapper.readValue(
                URLDecoder.decode(cookie1.getValue(), StandardCharsets.UTF_8),
                TypeFactory.defaultInstance()
                        .constructCollectionType(LinkedHashSet.class, Long.class)
        ));
        for(Long o: set) {
            log.info("wishlist: " + o + "");
        }
        modelAndView.addObject("recentList",
                new ArrayList<>(objectMapper.readValue(
                        URLDecoder.decode(cookie1.getValue(), StandardCharsets.UTF_8),
                        TypeFactory.defaultInstance()
                                .constructCollectionType(LinkedHashSet.class, Long.class)
                )
        ));
        return modelAndView;
    }

    private void deleteRecent(List<Long> recentList, Long id) {
        log.info("id: " + id);
        log.info(recentList.size() + "");
        recentList.remove(id);
        log.info(recentList.size() + "");
    }
}
