package shop.yesaladin.front.common;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.common.exception.RestException;

/**
 * js에서 예외처리 메세지를 html로 받지않게 하기 위해 사용
 *
 * @author 배수한
 * @since 1.0
 */

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {
    @ExceptionHandler(RestException.class)
    public ResponseDto<Void> handleRestException(Exception e){
        return ResponseDto.<Void>builder()
                .status(HttpStatus.BAD_REQUEST)
                .success(false)
                .errorMessages(List.of(e.getMessage()))
                .build();
    }
}
