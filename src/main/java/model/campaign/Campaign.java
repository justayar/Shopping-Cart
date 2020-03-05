package model.campaign;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.category.Category;

/**
 * @author canemreayar
 */

@Data
@AllArgsConstructor
public abstract class Campaign {

    Category category;
    double discountRate;
    int minNumberOfQuantityForCampaign;

    public abstract double getDiscountAmount(double totalAmount);

    public boolean checkCampaignValidity(int numberOfItemsInRelatedCampaignCategory) {
        return numberOfItemsInRelatedCampaignCategory >= minNumberOfQuantityForCampaign;
    }
}
