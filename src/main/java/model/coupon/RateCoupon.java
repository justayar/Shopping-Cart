package model.coupon;

import constants.ShoppingCartConstants;

public class RateCoupon extends Coupon {

    @Override
    public double getDiscountAmount(double totalCartAmount) {
        return totalCartAmount * discountRate * ShoppingCartConstants.RATE_DISCOUNT_PERCENTAGE;
    }
}
