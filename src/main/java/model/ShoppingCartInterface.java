package model;

import model.campaign.Campaign;
import model.coupon.Coupon;
import model.product.Product;

import java.util.List;

public interface ShoppingCartInterface {

    double getTotalAmountAfterDiscounts();
    double getCouponDiscount();
    double getCampaignDiscount();
    double getDeliveryCost();

    void addItem(Product product,int quantity);
    void applyDiscounts(List<Campaign> campaigns);
    void applyCoupon(Coupon coupon);
}
