package model;

import lombok.*;
import model.campaign.Campaign;
import model.category.Category;
import model.coupon.Coupon;
import model.product.Product;
import service.CampaignDiscountCalculator;
import service.CouponDiscountCalculator;
import service.DeliveryCostCalculator;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
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

    private DeliveryCostCalculator deliveryCostCalculator;

    private String resultText = "";


    public ShoppingCart(DeliveryCostCalculator deliveryCostCalculator){
        shoppingCartList = new HashMap<>();
        this.deliveryCostCalculator = deliveryCostCalculator;
        campaigns = new ArrayList<>();
        campaignDiscountAmount = 0.0;
        couponDiscountAmount = 0.0;

    }

    public double getDeliveryCost(){
        if(!shoppingCartList.isEmpty()){
            return deliveryCostCalculator.calculateFor(deliveryCostCalculator.getNumberOfDeliveries(shoppingCartList),
                    deliveryCostCalculator.getNumberOfDistinctProducts(shoppingCartList));
        }

        return 0;

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

        if(discounts == null)
            throw new NullPointerException("There is no any valid campaigns to apply.");

        campaigns.addAll(discounts);

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

        if(coupon == null)
            throw new NullPointerException("There is no any valid coupon to apply.");

        this.coupon = coupon;

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
        shoppingCartList.forEach((product,quantity) ->
            totalCartAmount += (product.getPrice() * quantity));


        return totalCartAmount;
    }

    public String print(){


        Map<@NotNull Category, List<Product>> groupedShoppingCartList = shoppingCartList.keySet().stream().collect(Collectors.groupingBy(Product::getCategory));

        groupedShoppingCartList.forEach(((category, products) ->
        {
            for(Product product : products){
                resultText += "\n CategoryName: "+category.getTitle().toUpperCase()+
                        " ProductName: "+product.getTitle().toUpperCase()+
                        " Quantity: "+shoppingCartList.get(product)+
                        " Unit Price: "+product.getPrice();
            }
        }));

        resultText +="\n TotalPrice: "+getTotalAmount();
        resultText +="\n TotalDiscount: "+Double.sum(getCampaignDiscount(),getCouponDiscount());

        return resultText;
    }

}
