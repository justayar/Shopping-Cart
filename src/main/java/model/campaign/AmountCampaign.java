package model.campaign;

import lombok.Data;
import model.category.Category;

@Data
public class AmountCampaign extends Campaign {

    public AmountCampaign(Category category, double discountRate, int minNumberOfQuantityForCampaign) {
        super(category, discountRate, minNumberOfQuantityForCampaign);
    }

    @Override
    public double getDiscountAmount(double totalAmount) {
        return discountRate;
    }


}
