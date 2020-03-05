package service;

import model.category.Category;
import model.product.Product;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class DeliveryCostCalculatorTest {

    private DeliveryCostCalculator deliveryCostCalculator;

    private Map<Product,Integer> shoppingCartList;

    @Before
    public void init() {

        shoppingCartList = new HashMap<>();
        Category fruits = new Category("fruits");
        Product apple = new Product("apple",2.0,fruits);
        shoppingCartList.put(apple,2);

        deliveryCostCalculator = new DeliveryCostCalculator(5.0,2.0);

    }

    @Test
    public void calculateDeliveryCostWhenCostsAreSet(){

        double deliveryCost = deliveryCostCalculator.calculateFor(1, 2);

        assertEquals(11.99,deliveryCost,0);
    }

    @Test
    public void numberOfDeliveriesCalculatedSuccessfullyWhenThereAreTwoCategories(){

        Category vegetables = new Category("vegetables");
        Product tomato = new Product("tomato",1.0,vegetables);
        shoppingCartList.put(tomato,1);

        int numberOfDeliveries = deliveryCostCalculator.getNumberOfDeliveries(shoppingCartList);

        assertEquals(2,numberOfDeliveries);

    }

    @Test
    public void numberOfDeliveriesCalculatedSuccessfullyWhenThereIsSingleCategory(){

        Category fruits = new Category("fruits");
        Product apple = new Product("apple",2.0,fruits);
        shoppingCartList.put(apple,2);

        int numberOfDeliveries = deliveryCostCalculator.getNumberOfDeliveries(shoppingCartList);

        assertEquals(1,numberOfDeliveries);

    }

    @Test
    public void numberOfDistinctProductsCalculatedSuccessfullyWhenShoppingListIsNull(){

        int numberOfDistinctProducts = deliveryCostCalculator.getNumberOfDistinctProducts(null);

        assertEquals(0,numberOfDistinctProducts);

    }

    @Test
    public void numberOfDistinctProductsCalculatedSuccessfullyWhenShoppingListIsSet(){

        int numberOfDistinctProducts = deliveryCostCalculator.getNumberOfDistinctProducts(shoppingCartList);

        assertEquals(1,numberOfDistinctProducts);

    }



}