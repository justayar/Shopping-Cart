package model.campaign;

import constants.ShoppingCartConstants;
import lombok.Data;
import model.category.Category;

@Data
public class RateCampaign extends Campaign {

    public Category category;
    private double discountRate;


    @Override
    public double getDiscountAmount(double totalAmount) {
        return totalAmount * discountRate * ShoppingCartConstants.RATE_DISCOUNT_PERCENTAGE;
    }

}
