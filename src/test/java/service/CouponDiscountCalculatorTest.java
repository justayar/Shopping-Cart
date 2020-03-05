package service;

import model.coupon.Coupon;
import model.coupon.RateCoupon;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;


public class CouponDiscountCalculatorTest {

    private CouponDiscountCalculator couponDiscountCalculator;

    @Before
    public void init() {


        couponDiscountCalculator = new CouponDiscountCalculator();

    }

    @Test(expected = NullPointerException.class)
    public void throwExceptionWhenCouponIsNull(){

        couponDiscountCalculator.calculateCouponDiscountAmount(null,100);

    }

    @Test
    public void couponDiscountAmountIsZeroWhenCouponIsNotValid(){

        Coupon coupon = new RateCoupon(100,10);

        double couponDiscountAmount = couponDiscountCalculator.calculateCouponDiscountAmount(coupon, 90);

        assertEquals(0,couponDiscountAmount,0);
    }

    @Test
    public void couponDiscountAmountWhenCouponIsValid(){

        Coupon coupon = new RateCoupon(100,10);

        double couponDiscountAmount = couponDiscountCalculator.calculateCouponDiscountAmount(coupon, 120);

        assertEquals(12,couponDiscountAmount,0);

    }

}