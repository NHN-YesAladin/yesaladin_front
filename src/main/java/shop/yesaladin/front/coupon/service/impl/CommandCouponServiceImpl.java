package shop.yesaladin.front.coupon.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import shop.yesaladin.coupon.trigger.CouponTypeCode;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.coupon.dto.AmountCouponCreateDto;
import shop.yesaladin.front.coupon.dto.CouponCreateDto;
import shop.yesaladin.front.coupon.dto.CouponCreateRequestDto;
import shop.yesaladin.front.coupon.dto.CouponCreateResponseDto;
import shop.yesaladin.front.coupon.dto.PointCouponCreateDto;
import shop.yesaladin.front.coupon.dto.RateCouponCreateDto;
import shop.yesaladin.front.coupon.exception.InvalidCouponTypeCodeException;
import shop.yesaladin.front.coupon.service.inter.CommandCouponService;

@RequiredArgsConstructor
@Service
public class CommandCouponServiceImpl implements CommandCouponService {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;
    private final ObjectMapper objectMapper;

    @Override
    public CouponCreateResponseDto createCouponTemplate(CouponCreateRequestDto createDto) {
        CouponCreateDto requestDto = generateDtoFromRequestDto(createDto);
        String queryParamName = getQueryParamName(createDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(
                requestDto.toMap(),
                httpHeaders
        );
        try {
            ResponseEntity<CouponCreateResponseDto> mapResponseEntity = restTemplate.postForEntity(
                    gatewayConfig.getCouponUrl() + "/v1/coupons?" + queryParamName,
                    httpEntity,
                    CouponCreateResponseDto.class
            );
            return mapResponseEntity.getBody();
        } catch (HttpClientErrorException e) {
            return getResponseDtoWithErrorMessageList(e);
        }
    }

    private CouponCreateResponseDto getResponseDtoWithErrorMessageList(HttpClientErrorException e) {
        try {
            return objectMapper.readValue(
                    e.getResponseBodyAsString(),
                    CouponCreateResponseDto.class
            );
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    private CouponCreateDto generateDtoFromRequestDto(CouponCreateRequestDto createDto) {
        if (CouponTypeCode.POINT.name().equals(createDto.getCouponTypeCode())) {
            return createRequestDtoToPointCouponCreateDto(createDto);
        } else if (CouponTypeCode.FIXED_RATE.name().equals(createDto.getCouponTypeCode())) {
            return createRequestDtoToRateCouponCreateDto(createDto);
        } else if (CouponTypeCode.FIXED_PRICE.name().equals(createDto.getCouponTypeCode())) {
            return createRequestDtoToAmountCouponCreateDto(createDto);
        }
        throw new InvalidCouponTypeCodeException(createDto.getCouponTypeCode());
    }

    private String getQueryParamName(CouponCreateRequestDto createDto) {
        if (CouponTypeCode.POINT.name().equals(createDto.getCouponTypeCode())) {
            return "point";
        } else if (CouponTypeCode.FIXED_RATE.name().equals(createDto.getCouponTypeCode())) {
            return "rate";
        } else if (CouponTypeCode.FIXED_PRICE.name().equals(createDto.getCouponTypeCode())) {
            return "amount";
        }

        throw new InvalidCouponTypeCodeException(createDto.getCouponTypeCode());
    }

    private PointCouponCreateDto createRequestDtoToPointCouponCreateDto(CouponCreateRequestDto createDto) {
        return new PointCouponCreateDto(
                createDto.getTriggerCode(),
                createDto.getName(),
                createDto.getIsUnlimited(),
                createDto.getQuantity(),
                createDto.getCouponImage().getResource(),
                createDto.getDuration(),
                createDto.getExpirationDate(),
                createDto.getCouponTypeCode(),
                createDto.getDiscountAmount()
        );
    }

    private RateCouponCreateDto createRequestDtoToRateCouponCreateDto(CouponCreateRequestDto createDto) {
        return new RateCouponCreateDto(
                createDto.getTriggerCode(),
                createDto.getName(),
                createDto.getIsUnlimited(),
                createDto.getQuantity(),
                createDto.getCouponImage().getResource(),
                createDto.getDuration(),
                createDto.getExpirationDate(),
                createDto.getCouponTypeCode(),
                createDto.getMinOrderAmount(),
                createDto.getMaxDiscountAmount(),
                createDto.getDiscountAmount(),
                createDto.isCanBeOverlapped(),
                createDto.getCouponBoundCode(),
                null,
                null
        );
    }

    private AmountCouponCreateDto createRequestDtoToAmountCouponCreateDto(CouponCreateRequestDto createDto) {
        return new AmountCouponCreateDto(
                createDto.getTriggerCode(),
                createDto.getName(),
                createDto.getIsUnlimited(),
                createDto.getQuantity(),
                createDto.getCouponImage().getResource(),
                createDto.getDuration(),
                createDto.getExpirationDate(),
                createDto.getCouponTypeCode(),
                createDto.getMinOrderAmount(),
                createDto.getDiscountAmount(),
                createDto.isCanBeOverlapped(),
                createDto.getCouponBoundCode(),
                null,
                null
        );
    }
}