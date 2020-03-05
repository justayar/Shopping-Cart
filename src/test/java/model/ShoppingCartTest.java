package model;

import junit.framework.TestCase;
import model.campaign.AmountCampaign;
import model.campaign.Campaign;
import model.campaign.RateCampaign;
import model.category.Category;
import model.coupon.Coupon;
import model.coupon.RateCoupon;
import model.product.Product;
import org.junit.Before;
import org.junit.Test;
import service.DeliveryCostCalculator;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

public class ShoppingCartTest {

    private ShoppingCart shoppingCart;
    private DeliveryCostCalculator deliveryCostCalculator;

    @Before
    public void init() {
        deliveryCostCalculator = new DeliveryCostCalculator(2.0,3.0);
        shoppingCart = new ShoppingCart(deliveryCostCalculator);

    }

    @Test
    public void returnZeroDeliveryCostWhenTheShoppingCartIsEmpty(){

        double deliveryCost = shoppingCart.getDeliveryCost();

        assertEquals(0,deliveryCost,0);
    }

    @Test
    public void returnCorrectDeliveryCostWhenTheShoppingCartIsNotEmpty(){

        mockShoppingCartItems();

        double deliveryCost = shoppingCart.getDeliveryCost();

        assertEquals(26.99,deliveryCost,0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionWhenAddingProductWithZeroQuantity(){

        Category fruits = new Category("fruits");
        Product apple = new Product("apple",2.0,fruits);

        shoppingCart.addItem(apple,0);

    }

    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionWhenAddingProductWithQuantityIsLowerThanZero(){

        Category fruits = new Category("fruits");
        Product apple = new Product("apple",2.0,fruits);

        shoppingCart.addItem(apple,-1);

    }

    @Test
    public void addItemSuccessfullyWhenAddingProductWithQuantityIsBiggerThanZero(){

        Category fruits = new Category("fruits");
        Product apple = new Product("apple",2.0,fruits);

        shoppingCart.addItem(apple,1);

        assertEquals(1,shoppingCart.getShoppingCartList().size());
        assertEquals(1,shoppingCart.getShoppingCartList().get(apple).intValue());
    }

    @Test
    public void notAddItemSuccessfullyWhenAddingProductIsNull(){

        Category fruits = new Category("fruits");
        Product apple = null;

        shoppingCart.addItem(apple,1);

        assertEquals(0,shoppingCart.getShoppingCartList().size());
    }

    @Test
    public void updateItemQuantitySuccessfullyWhenAddingProductIsAlreadyInCart(){

        mockShoppingCartItems();
        Category fruits = new Category("fruits");
        Product apple = new Product("apple",2.0,fruits);

        shoppingCart.addItem(apple,1);

        assertEquals(2,shoppingCart.getShoppingCartList().get(apple).intValue());
    }

    @Test
    public void addItemSuccessfullyWhenAddingProductIsNotInCart(){

        mockShoppingCartItems();
        Category fruits = new Category("fruits");
        Product orange = new Product("",2.0,fruits);

        shoppingCart.addItem(orange,1);

        assertEquals(1,shoppingCart.getShoppingCartList().get(orange).intValue());
    }


    @Test(expected = NullPointerException.class)
    public void throwExceptionWhenThereIsNoDiscount(){

        mockShoppingCartItems();
        List<Campaign> campaignList = null;

        shoppingCart.applyDiscounts(campaignList);

        assertEquals(0,shoppingCart.getCampaigns().size());
    }

    @Test
    public void applyDiscountSuccessfullyWhenThereAreDiscounts(){

        mockShoppingCartItems();
        List<Campaign> campaignList = new ArrayList<>();
        Category fruits = new Category("fruits");
        Campaign campaign1 = new RateCampaign(fruits,50.0,2);
        Campaign campaign2 = new AmountCampaign(fruits,50.0,2);
        campaignList.add(campaign1);
        campaignList.add(campaign2);

        shoppingCart.applyDiscounts(campaignList);

        assertEquals(2,shoppingCart.getCampaigns().size());
    }

    @Test
    public void zeroCampaignDiscountAmountWhenThereIsNoCampaign(){

        mockShoppingCartItems();

        double campaignDiscount = shoppingCart.getCampaignDiscount();

        assertEquals(0.0,campaignDiscount,0);
    }

    @Test
    public void campaignDiscountAmountWhenThereIsSingleCampaign(){

        mockShoppingCartItems();
        List<Campaign> campaignList = new ArrayList<>();
        Category fruits = new Category("fruits");
        Campaign campaign1 = new RateCampaign(fruits,50.0,1);
        campaignList.add(campaign1);

        shoppingCart.applyDiscounts(campaignList);
        double campaignDiscount = shoppingCart.getCampaignDiscount();


        assertEquals(6.0,campaignDiscount,0);
    }

    @Test
    public void campaignDiscountAmountWhenThereAreMultipleCampaigns(){

        mockShoppingCartItems();
        List<Campaign> campaignList = new ArrayList<>();
        Category fruits = new Category("fruits");
        Campaign campaign1 = new RateCampaign(fruits,50.0,1);
        Category vegetables = new Category("vegetables");
        Campaign campaign2 = new RateCampaign(vegetables,50.0,1);
        campaignList.add(campaign1);
        campaignList.add(campaign2);

        shoppingCart.applyDiscounts(campaignList);
        double campaignDiscount = shoppingCart.getCampaignDiscount();


        assertEquals(12.5,campaignDiscount,0);
    }

    @Test(expected = NullPointerException.class)
    public void throwExceptionWhenApplyingNullCoupon(){

        mockShoppingCartItems();
        Coupon coupon = null;

        shoppingCart.applyCoupon(coupon);

        assertEquals(0,shoppingCart.getCampaigns().size());
    }

    @Test
    public void applyCouponSuccessfullyWhenApplyingValidCoupon(){

        mockShoppingCartItems();
        Coupon coupon = new RateCoupon(10,10);


        shoppingCart.applyCoupon(coupon);

        assertEquals(coupon,shoppingCart.getCoupon());
    }

    @Test
    public void zeroCouponDiscountAmountWhenThereIsNoCoupon(){

        mockShoppingCartItems();

        double couponDiscount = shoppingCart.getCouponDiscount();

        assertEquals(0,couponDiscount,0);
    }


    @Test
    public void couponDiscountAmountWhenThereIsACoupon(){

        mockShoppingCartItems();
        Coupon coupon = new RateCoupon(10,10);

        shoppingCart.applyCoupon(coupon);
        double couponDiscount = shoppingCart.getCouponDiscount();

        assertEquals(7.0,couponDiscount,0);
    }

    @Test
    public void couponDiscountAmountWhenThereIsACouponAndCampaign(){

        mockShoppingCartItems();
        List<Campaign> campaignList = new ArrayList<>();
        Category fruits = new Category("fruits");
        Campaign campaign1 = new RateCampaign(fruits,50.0,1);
        campaignList.add(campaign1);
        Coupon coupon = new RateCoupon(10,10);

        shoppingCart.applyDiscounts(campaignList);
        shoppingCart.applyCoupon(coupon);
        double couponDiscount = shoppingCart.getCouponDiscount();

        assertEquals(6.4,couponDiscount,0);
    }

    @Test
    public void totalAmountAfterDiscountsWhenThereIsACampaign(){

        mockShoppingCartItems();
        List<Campaign> campaignList = new ArrayList<>();
        Category fruits = new Category("fruits");
        Campaign campaign1 = new RateCampaign(fruits,50.0,1);
        campaignList.add(campaign1);

        shoppingCart.applyDiscounts(campaignList);
        double totalAmountAfterDiscounts = shoppingCart.getTotalAmountAfterDiscounts();

        assertEquals(64.0,totalAmountAfterDiscounts,0);
    }

    @Test
    public void totalAmountAfterDiscountsWhenThereIsACoupon(){

        mockShoppingCartItems();
        Coupon coupon = new RateCoupon(10,10);

        shoppingCart.applyCoupon(coupon);
        double totalAmountAfterDiscounts = shoppingCart.getTotalAmountAfterDiscounts();

        assertEquals(63.0,totalAmountAfterDiscounts,0);
    }

    @Test
    public void totalAmountAfterDiscountsWhenThereIsACouponAndCampaign(){

        mockShoppingCartItems();
        List<Campaign> campaignList = new ArrayList<>();
        Category fruits = new Category("fruits");
        Campaign campaign1 = new RateCampaign(fruits,50.0,1);
        campaignList.add(campaign1);
        Coupon coupon = new RateCoupon(10,10);

        shoppingCart.applyDiscounts(campaignList);
        shoppingCart.applyCoupon(coupon);
        double totalAmountAfterDiscounts = shoppingCart.getTotalAmountAfterDiscounts();

        assertEquals(58.2,totalAmountAfterDiscounts,0);
    }

    @Test
    public void printSuccessfully(){

        mockShoppingCartItems();
        List<Campaign> campaignList = new ArrayList<>();
        Category fruits = new Category("fruits");
        Campaign campaign1 = new RateCampaign(fruits,50.0,1);
        campaignList.add(campaign1);
        Coupon coupon = new RateCoupon(10,10);

        shoppingCart.applyDiscounts(campaignList);
        shoppingCart.applyCoupon(coupon);

        String resultText = shoppingCart.print();

        String expectedResult = "\n" +
                " CategoryName: FRUITS ProductName: BANANA Quantity: 2 Unit Price: 5.0\n" +
                " CategoryName: FRUITS ProductName: APPLE Quantity: 1 Unit Price: 2.0\n" +
                " CategoryName: MOVIES ProductName: BATMAN Quantity: 1 Unit Price: 20.0\n" +
                " CategoryName: MOVIES ProductName: STARTREK Quantity: 1 Unit Price: 25.0\n" +
                " CategoryName: VEGETABLES ProductName: PATATO Quantity: 5 Unit Price: 2.0\n" +
                " CategoryName: VEGETABLES ProductName: TOMATO Quantity: 3 Unit Price: 1.0\n" +
                " TotalPrice: 70.0\n" +
                " TotalDiscount: 11.8";

        TestCase.assertEquals(expectedResult,resultText);
    }


    private void mockShoppingCartItems(){

        Category fruits = new Category("fruits");

        Product apple = new Product("apple",2.0,fruits);

        Product banana = new Product("banana",5.0,fruits);

        Category vegetables = new Category("vegetables");

        Product tomato = new Product("tomato",1.0,vegetables);

        Product patato = new Product("patato",2.0,vegetables);

        Category movies = new Category("movies");

        Product batman = new Product("batman",20.0,movies);

        Product starTrek = new Product("starTrek",25.0,movies);


        shoppingCart.addItem(apple,1);
        shoppingCart.addItem(banana,2);
        shoppingCart.addItem(tomato,3);
        shoppingCart.addItem(patato,5);
        shoppingCart.addItem(batman,1);
        shoppingCart.addItem(starTrek,1);

    }



}
