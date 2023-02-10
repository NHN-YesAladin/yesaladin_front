package shop.yesaladin.front.common.dto;

import java.util.List;
import lombok.Data;

/**
 * RestTemplate 요청 시 Back Server에서 발생하는 예외 응답 결과를 잡기 위한 DTO 입니다.
 *
 * @author 송학현
 * @since 1.0
 */
@Data
public class ExceptionResponseDto {

    private boolean success;
    private int status;
    private Object data;
    private List<String> errorMessages;
}
