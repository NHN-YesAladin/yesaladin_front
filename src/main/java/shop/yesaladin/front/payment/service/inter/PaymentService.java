package shop.yesaladin.front.payment.service.inter;

import com.fasterxml.jackson.databind.JsonNode;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.payment.dto.PaymentCompleteSimpleResponseDto;
import shop.yesaladin.front.payment.dto.PaymentRequestDto;

/**
 * 결제 관련 서비스 메서드를 정의한 인터페이스
 *
 * @author 배수한
 * @since 1.0
 */
public interface PaymentService {

    /**
     * 토스페이먼츠의 successUrl을 통해 결제 승인을 shop서버를 통해 받아오는 메서드
     *
     * @param requestDto 토스 결제를 위한 정보들이 담긴 dto
     * @return responseDto - 결제 정보
     */
    ResponseDto<PaymentCompleteSimpleResponseDto> confirm(PaymentRequestDto requestDto);
}
