package model.coupon;

public class AmountCoupon extends Coupon {

    @Override
    public double getDiscountAmount(double totalCartAmount) {
        return discountRate;
    }
}
