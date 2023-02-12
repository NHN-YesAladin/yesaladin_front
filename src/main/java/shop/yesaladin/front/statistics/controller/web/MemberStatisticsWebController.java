package shop.yesaladin.front.statistics.controller.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/manager")
public class MemberStatisticsWebController {

    @GetMapping("/statistics/members")
    public String memberStatistics() {


        return "manager/member/statistics";
    }
}
