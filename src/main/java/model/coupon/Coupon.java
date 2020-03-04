package model.coupon;

import lombok.Data;


/**
 * @author canemreayar
 */

@Data
public abstract class Coupon {

    double minPurchaseAmountForCoupon;
    double discountRate;

    public abstract double getDiscountAmount(double totalCartAmount);

    public boolean checkCouponValidity(double totalCartAmount) {
        return totalCartAmount >= minPurchaseAmountForCoupon;
    }
}
