package service;

import lombok.Data;
import model.coupon.Coupon;

@Data
public class CouponDiscountCalculator {

    public double calculateCouponDiscountAmount(Coupon coupon,double totalCartAmountAfterCampaigns){

        if(coupon == null)
            throw new NullPointerException("There are no any coupon to calculate coupon discount amount.");

        boolean isCouponValid = coupon.checkCouponValidity(totalCartAmountAfterCampaigns);

        if(isCouponValid)
            return coupon.getDiscountAmount(totalCartAmountAfterCampaigns);

        return 0;
    }


}
