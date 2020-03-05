package service;

import lombok.Data;
import model.campaign.Campaign;
import model.product.Product;

import java.util.Map;

@Data
public class CampaignDiscountCalculator {

    private int relatedDiscountCategoryItemQuantity;

    private double totalAmountForRelatedDiscountCategory;

    public double calculateCampaignDiscountAmount(Campaign campaign,Map<Product,Integer> shoppingCartList){

        int quantityForRelatedDiscountCategory = findRelatedDiscountCategoryItemQuantity(campaign,shoppingCartList);

        boolean isCampaignValid = campaign.checkCampaignValidity(quantityForRelatedDiscountCategory);

        if(isCampaignValid){
            double totalAmountForDiscount = calculateTotalAmountForRelatedDiscountCategory(campaign,shoppingCartList);
            return campaign.getDiscountAmount(totalAmountForDiscount);
        }

        return 0;

    }

    private int findRelatedDiscountCategoryItemQuantity(Campaign campaign,Map<Product,Integer> shoppingCartList){

        if(campaign == null)
            throw new NullPointerException("There is no any campaign to find related discount");

        relatedDiscountCategoryItemQuantity = 0;
            shoppingCartList.forEach((product, quantity) -> {
                if (product.getCategory().equals(campaign.getCategory())) {
                    relatedDiscountCategoryItemQuantity += quantity;
                }
            });


        return relatedDiscountCategoryItemQuantity;
    }

    private double calculateTotalAmountForRelatedDiscountCategory(Campaign campaign,Map<Product,Integer> shoppingCartList){

        totalAmountForRelatedDiscountCategory = 0;
        shoppingCartList.forEach((product,quantity) -> {
            if(product.getCategory().equals(campaign.getCategory())){
                totalAmountForRelatedDiscountCategory += (product.getPrice() * quantity);
            }
        });

        return totalAmountForRelatedDiscountCategory;
    }
}
