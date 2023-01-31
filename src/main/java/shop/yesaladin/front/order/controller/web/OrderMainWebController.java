package shop.yesaladin.front.order.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderMainWebController {

    @GetMapping("/test")
    public String getOrderSheet(Model model) {
        model.addAttribute("userPoint", 5000);

        return "/main/order/order-page";
    }

    @GetMapping("/member")
    public String getMemberOrderSheet(Model model) {
        return "/main/order/order-page";
    }

}
