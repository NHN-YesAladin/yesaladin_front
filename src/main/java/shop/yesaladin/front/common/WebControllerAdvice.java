package shop.yesaladin.front.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import shop.yesaladin.front.common.exception.ValidationFailedException;

@Slf4j
@ControllerAdvice
public class WebControllerAdvice {

    @ExceptionHandler(ValidationFailedException.class)
    public String handleValidationFailedException(ValidationFailedException ex, Model model) {
        log.error("", ex);

        model.addAttribute("exception", ex);
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        log.error("[Exception] : ", ex);
        model.addAttribute("exception", ex);
        return "error";
    }
}
