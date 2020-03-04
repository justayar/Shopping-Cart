package model;

import lombok.*;
import model.campaign.Campaign;
import model.category.Category;
import model.coupon.Coupon;
import model.product.Product;
import service.CampaignDiscountCalculator;
import service.CouponDiscountCalculator;
import service.DeliveryCostCalculator;
import util.StreamOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author canemreayar
 */

@Data
public class ShoppingCart implements ShoppingCartInterface {

    private double campaignDiscountAmount;

    private double couponDiscountAmount;

    private double totalCartAmount;

    private Map<Product,Integer> shoppingCartList;

    private List<Campaign> campaigns;

    private Coupon coupon;

    public double getDeliveryCost(){
        DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator();
        return deliveryCostCalculator.calculateFor(deliveryCostCalculator.getNumberOfDeliveries(shoppingCartList),
                                            deliveryCostCalculator.getNumberOfProducts(shoppingCartList));
    }

    public void addItem(Product product,int quantity){

        if(quantity <=0)
            throw new IllegalArgumentException("Number of added items must be bigger than zero");

        if(product != null){
            if(shoppingCartList.containsKey(product)){
                Integer currentQuantityOfAddedProduct = shoppingCartList.get(product);
                shoppingCartList.put(product, currentQuantityOfAddedProduct+quantity);
            }else{
                shoppingCartList.put(product,quantity);
            }
        }

    }

    public void applyDiscounts(List<Campaign> discounts){

        if(discounts != null){
            campaigns.addAll(discounts);
        }

    }

    public double getCampaignDiscount(){

        CampaignDiscountCalculator campaignDiscountCalculator = new CampaignDiscountCalculator();

        campaigns.forEach((campaign -> {
            double discountAmount = campaignDiscountCalculator.calculateCampaignDiscountAmount(campaign,shoppingCartList);
            campaignDiscountAmount += discountAmount;
        }));

        return campaignDiscountAmount;
    }

    public void applyCoupon(Coupon coupon){

        if(coupon!= null) {
            this.coupon = coupon;
        }

    }

    public double getCouponDiscount(){

        CouponDiscountCalculator couponDiscountCalculator = new CouponDiscountCalculator();

        double totalCartAmountAfterCampaigns = getTotalAmountAfterCampaigns();


        if(coupon != null){
            return couponDiscountCalculator.calculateCouponDiscountAmount(coupon,totalCartAmountAfterCampaigns);
        }
        return 0;
    }

    public double getTotalAmountAfterDiscounts() {

        return getTotalAmountAfterCampaigns() - getCouponDiscount();

    }

    private double getTotalAmountAfterCampaigns(){
        return getTotalAmount() - getCampaignDiscount();
    }

    private double getTotalAmount(){

        totalCartAmount = 0;
        shoppingCartList.forEach((product,quantity) -> {
            totalCartAmount += (product.getPrice() * quantity);
        });

        return totalCartAmount;
    }

}
