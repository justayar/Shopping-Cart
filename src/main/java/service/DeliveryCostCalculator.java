package service;

import lombok.*;
import model.ShoppingCart;
import model.category.Category;
import model.product.Product;
import util.StreamOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author canemreayar
 */

@Data
public class DeliveryCostCalculator {

    private double costPerDelivery;
    private double costPerProduct;

    private static final double FIXED_COST = 2.99;

    public double calculateFor(int numberOfDeliveries, int numberOfProducts){

        return (this.costPerDelivery * numberOfDeliveries)
                + (this.costPerProduct * numberOfProducts)
                + FIXED_COST;
    }

    public int getNumberOfDeliveries(Map<Product,Integer> shoppingCartList) {
        return getDistinctCategoriesInCart(shoppingCartList).size();
    }

    private List<Category> getDistinctCategoriesInCart(Map<Product,Integer> shoppingCartList){

        List<Product> singleCategoryProducts = shoppingCartList.keySet().stream().
                filter(StreamOperations.distinctByKey(Product::getCategory)).collect(Collectors.toList());

        List<Category> categories = new ArrayList<>();

        addCategoryOfEachDistinctCategoryProduct(singleCategoryProducts, categories);

        return categories;

    }

    private void addCategoryOfEachDistinctCategoryProduct(List<Product> singleCategoryProducts, List<Category> categories) {
        singleCategoryProducts.forEach(product -> {

            Category category = new Category();
            category.setTitle(product.getTitle());
            categories.add(category);
        });
    }

    public int getNumberOfProducts(Map<Product,Integer> shoppingCartList) {
        return getDistinctProductsInCart(shoppingCartList).size();
    }

    private List<Product> getDistinctProductsInCart(Map<Product,Integer> shoppingCartList){
        return shoppingCartList.keySet().stream().
                filter(StreamOperations.distinctByKey(Product::getTitle)).collect(Collectors.toList());
    }
}

