package shop.yesaladin.front.point.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import shop.yesaladin.front.point.service.inter.QueryPointHistoryService;

@RequiredArgsConstructor
@Controller
public class PointRestController {
    private final QueryPointHistoryService queryPointHistoryService;

    /**
     * 회원의 포인트를 반환합니다.
     *
     * @return 회원의 포인트
     * @author 최예린
     * @since 1.0
     */
    @GetMapping("/points")
    public long getMemberPoint() {
        return queryPointHistoryService.getMemberPoint();
    }

}
