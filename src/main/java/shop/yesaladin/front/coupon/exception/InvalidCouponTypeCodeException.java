package shop.yesaladin.front.coupon.exception;


/**
 * 유효하지 않은 쿠폰 타입이 들어왔을 때 발생하는 예외입니다.
 *
 * @author 김홍대
 * @since 1.0
 */
public class InvalidCouponTypeCodeException extends RuntimeException {

    public InvalidCouponTypeCodeException(String couponTypeCode) {
        super("Invalid coupon type: " + couponTypeCode);
    }
}
