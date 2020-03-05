package model.campaign;

import constants.ShoppingCartConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import model.category.Category;

@Data
public class RateCampaign extends Campaign {


    public RateCampaign(Category category, double discountRate, int minNumberOfQuantityForCampaign) {
        super(category, discountRate, minNumberOfQuantityForCampaign);
    }

    @Override
    public double getDiscountAmount(double totalAmount) {
        return totalAmount * discountRate * ShoppingCartConstants.PERCENTAGE_RATE;
    }

}
