package shop.yesaladin.front.order.controller;

import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {
    @GetMapping
    public String getOrderSheet(Model model) {
        model.addAttribute("userPoint", 5000);
        return "order/order-page";
    }

}
