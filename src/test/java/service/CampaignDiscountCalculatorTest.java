package service;

import model.campaign.Campaign;
import model.campaign.RateCampaign;
import model.category.Category;
import model.product.Product;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CampaignDiscountCalculatorTest {

    private CampaignDiscountCalculator campaignDiscountCalculator;

    private Map<Product,Integer> shoppingCartList;

    @Before
    public void init() {

        shoppingCartList = new HashMap<>();
        Category fruits = new Category("fruits");
        Product apple = new Product("apple",2.0,fruits);
        shoppingCartList.put(apple,2);

        Category vegetables = new Category("vegetables");
        Product tomato = new Product("tomato",1.0,vegetables);
        shoppingCartList.put(tomato,1);

        campaignDiscountCalculator = new CampaignDiscountCalculator();

    }

    @Test(expected = NullPointerException.class)
    public void campaignDiscountAmountIsZeroWhenCampaignIsNull(){

        campaignDiscountCalculator.calculateCampaignDiscountAmount(null,shoppingCartList);

    }

    @Test
    public void campaignDiscountAmountWhenCampaignIsNotValid(){

        Category fruits = new Category("fruits");
        Campaign campaign = new RateCampaign(fruits,50.0,5);

        double campaignDiscountAmount = campaignDiscountCalculator.calculateCampaignDiscountAmount(campaign, shoppingCartList);

        assertEquals(0,campaignDiscountAmount,0);

    }

    @Test
    public void campaignDiscountAmountWhenCampaignIsValid(){

        Category fruits = new Category("fruits");
        Campaign campaign = new RateCampaign(fruits,50.0,1);

        double campaignDiscountAmount = campaignDiscountCalculator.calculateCampaignDiscountAmount(campaign, shoppingCartList);

        assertEquals(2.0,campaignDiscountAmount,0);

    }

}