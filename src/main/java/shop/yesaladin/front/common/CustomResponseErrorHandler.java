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
import shop.yesaladin.front.common.dto.ExceptionResponseDto;
import shop.yesaladin.front.common.exception.Custom4xxException;
import shop.yesaladin.front.common.exception.CustomForbiddenException;
import shop.yesaladin.front.common.exception.CustomGatewayException;
import shop.yesaladin.front.common.exception.CustomServerException;
import shop.yesaladin.front.common.exception.CustomUnauthorizedException;

/**
 * RestTemplate으로 서버에 보낸 요청의 예외 발생 시 HttpStatus 코드에 따라 예외를 발생 시키기 위한 Handler 입니다.
 *
 * @author 송학현
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
        ExceptionResponseDto exception = objectMapper.readValue(
                messageBody,
                ExceptionResponseDto.class
        );

        int status = exception.getStatus();
        List<String> errorMessages = exception.getErrorMessages();

        if (status == HttpStatus.UNAUTHORIZED.value()) {
            throw new CustomUnauthorizedException(errorMessages.toString());
        } else if (status == HttpStatus.NOT_FOUND.value()
                || status == HttpStatus.BAD_REQUEST.value()
                || status == HttpStatus.METHOD_NOT_ALLOWED.value()
                || status == HttpStatus.CONFLICT.value()) {
            throw new Custom4xxException(errorMessages.toString());
        } else if (status == HttpStatus.FORBIDDEN.value()) {
            throw new CustomForbiddenException(errorMessages.toString());
        } else if (status == HttpStatus.BAD_GATEWAY.value()) {
            throw new CustomGatewayException(errorMessages.toString());
        } else if (status == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            throw new CustomServerException(errorMessages.toString());
        }
    }
}
