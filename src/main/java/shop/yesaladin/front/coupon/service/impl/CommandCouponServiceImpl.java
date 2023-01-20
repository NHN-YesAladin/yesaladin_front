package shop.yesaladin.front.coupon.service.impl;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import shop.yesaladin.coupon.trigger.CouponTypeCode;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.coupon.dto.CouponCreateDto;
import shop.yesaladin.front.coupon.dto.CouponCreateRequestDto;
import shop.yesaladin.front.coupon.dto.PointCouponCreateDto;
import shop.yesaladin.front.coupon.dto.RateCouponCreateDto;
import shop.yesaladin.front.coupon.service.inter.CommandCouponService;

@RequiredArgsConstructor
@Service
public class CommandCouponServiceImpl implements CommandCouponService {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    @Override
    public String createCouponTemplate(CouponCreateRequestDto createDto) {
        CouponCreateDto requestDto = generateDtoFromRequestDto(createDto);

        ResponseEntity<Map> mapResponseEntity = restTemplate.postForEntity(
                "http://localhost:8080/v1/coupons?point",
                requestDto,
                Map.class
        );
        return (String) mapResponseEntity.getBody().get("name");

    }

    private CouponCreateDto generateDtoFromRequestDto(CouponCreateRequestDto createDto) {
        if (CouponTypeCode.POINT.name().equals(createDto.getCouponTypeCode())) {
            return new PointCouponCreateDto(
                    createDto.getTriggerCode(),
                    createDto.getName(),
                    createDto.getIsUnlimited(),
                    createDto.getQuantity(),
                    null,
                    createDto.getDuration(),
                    createDto.getExpirationDate(),
                    createDto.getCouponTypeCode(),
                    createDto.getDiscountAmount()
            );
        } else if (CouponTypeCode.FIXED_RATE.name().equals(createDto.getCouponTypeCode())) {
            return new RateCouponCreateDto(
                    createDto.getTriggerCode(),
                    createDto.getName(),
                    createDto.getIsUnlimited(),
                    createDto.getQuantity(),
                    null,
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

        return null;
    }
}
