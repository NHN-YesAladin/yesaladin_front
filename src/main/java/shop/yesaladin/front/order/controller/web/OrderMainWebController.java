package shop.yesaladin.front.order.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderMainWebController {
    @GetMapping
    public String getOrderSheet(Model model) {
        model.addAttribute("userPoint", 5000);
        return "/main/order/order-page";
    }

}
