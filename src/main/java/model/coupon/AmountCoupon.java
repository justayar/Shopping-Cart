package model.coupon;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AmountCoupon extends Coupon {

    public AmountCoupon(double minPurchaseAmountForCoupon, double discountRate) {
        super(minPurchaseAmountForCoupon, discountRate);
    }

    @Override
    public double getDiscountAmount(double totalCartAmount) {
        return discountRate;
    }
}
