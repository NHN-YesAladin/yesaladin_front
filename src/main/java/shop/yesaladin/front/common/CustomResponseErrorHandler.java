package shop.yesaladin.front.common;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.common.exception.*;

/**
 * RestTemplate으로 서버에 보낸 요청의 예외 발생 시 HttpStatus 코드에 따라 예외를 발생 시키기 위한 Handler 입니다.
 *
 * @author 송학현
 * @author 이수정
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomResponseErrorHandler implements ResponseErrorHandler {

    private final ObjectMapper objectMapper;

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return (
                response.getStatusCode().series() == CLIENT_ERROR
                        || response.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        InputStream is = response.getBody();
        String messageBody = StreamUtils.copyToString(is, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        ResponseDto exception = objectMapper.readValue(messageBody, ResponseDto.class);

        int status = response.getStatusCode().value();
        List<String> errorMessages = exception.getErrorMessages();

        if (status == HttpStatus.UNAUTHORIZED.value()) {
            throw new CustomUnauthorizedException(errorMessages.toString());
        } else if (status == HttpStatus.NOT_FOUND.value()) {
            throw new CustomNotFoundException(errorMessages.toString());
        } else if (status == HttpStatus.BAD_REQUEST.value()) {
            throw new CustomBadRequestException(errorMessages.toString());
        } else if (status == HttpStatus.METHOD_NOT_ALLOWED.value()) {
            throw new CustomMethodNotAllowedException(errorMessages.toString());
        } else if (status == HttpStatus.CONFLICT.value()) {
            throw new CustomConflictException(errorMessages.toString());
        } else if (status == HttpStatus.FORBIDDEN.value()) {
            throw new CustomForbiddenException(errorMessages.toString());
        } else if (status == HttpStatus.BAD_GATEWAY.value()) {
            throw new CustomGatewayException(errorMessages.toString());
        } else if (status == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            throw new CustomServerException(errorMessages.toString());
        }
    }
}
