package model.coupon;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * @author canemreayar
 */

@Data
@AllArgsConstructor
public abstract class Coupon {

    double minPurchaseAmountForCoupon;
    double discountRate;

    public abstract double getDiscountAmount(double totalCartAmount);

    public boolean checkCouponValidity(double totalCartAmount) {
        return totalCartAmount >= minPurchaseAmountForCoupon;
    }
}
