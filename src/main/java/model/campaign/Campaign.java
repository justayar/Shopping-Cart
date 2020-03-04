package model.campaign;

import lombok.Data;
import model.category.Category;

/**
 * @author canemreayar
 */

@Data
public abstract class Campaign {

    int minNumberOfQuantityForCampaign;
    Category category;
    double discountRate;

    public abstract double getDiscountAmount(double totalAmount);

    public boolean checkCampaignValidity(int numberOfItemsInRelatedCampaignCategory) {
        return numberOfItemsInRelatedCampaignCategory >= minNumberOfQuantityForCampaign;
    }
}
