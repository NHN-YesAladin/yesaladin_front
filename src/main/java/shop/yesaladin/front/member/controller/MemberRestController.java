package shop.yesaladin.front.member.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MemberRestController {

    @GetMapping("/checkNickname/{nickname}")
    public boolean nicknameDuplicateCheck(@PathVariable String nickname) {
        log.info("nickname={}", nickname);
        return true;
    }

    @GetMapping("/checkLoginId/{loginId}")
    public boolean loginIdDuplicateCheck(@PathVariable String loginId) {
        log.info("nickname={}", loginId);
        return true;
    }

    @GetMapping("/checkEmail/{email}")
    public boolean emailDuplicateCheck(@PathVariable String email) {
        log.info("email={}", email);
        return true;
    }

    @GetMapping("/checkPhone/{phone}")
    public boolean phoneDuplicateCheck(@PathVariable String phone) {
        log.info("phone={}", phone);
        return true;
    }
}
