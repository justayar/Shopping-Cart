package model.campaign;

import lombok.Data;
import model.category.Category;

@Data
public class AmountCampaign extends Campaign {


    @Override
    public double getDiscountAmount(double totalAmount) {
        return discountRate;
    }


}
