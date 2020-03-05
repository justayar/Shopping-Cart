package model.coupon;

import constants.ShoppingCartConstants;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class RateCoupon extends Coupon {

    public RateCoupon(double minPurchaseAmountForCoupon, double discountRate) {
        super(minPurchaseAmountForCoupon, discountRate);
    }

    @Override
    public double getDiscountAmount(double totalCartAmount) {
        return totalCartAmount * discountRate * ShoppingCartConstants.PERCENTAGE_RATE;
    }
}
