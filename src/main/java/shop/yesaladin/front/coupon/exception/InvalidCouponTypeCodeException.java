package shop.yesaladin.front.coupon.exception;

public class InvalidCouponTypeCodeException extends RuntimeException {

    public InvalidCouponTypeCodeException(String couponTypeCode) {
        super("Invalid coupon type: " + couponTypeCode);
    }
}
