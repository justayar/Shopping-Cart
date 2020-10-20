package service;

import constants.ShoppingCartConstants;
import lombok.*;
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
@AllArgsConstructor
public class DeliveryCostCalculator {

    private double costPerDelivery;
    private double costPerProduct;

    public double calculateFor(int numberOfDeliveries, int numberOfProducts){

        double deliveryCost = (this.costPerDelivery * numberOfDeliveries)
                + (this.costPerProduct * numberOfProducts)
                + ShoppingCartConstants.DELIVERY_FIXED_COST;

        return Double.parseDouble(ShoppingCartConstants.DOUBLE_TWO_DIGIT_AFTER_DOT.format(deliveryCost));

    }

    public int getNumberOfDeliveries(Map<Product,Integer> shoppingCartList) {
        if(shoppingCartList != null)
            return getDistinctCategoriesInCart(shoppingCartList).size();

        return 0;
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

            Category category = new Category(product.getTitle());
            categories.add(category);
        });
    }

    public int getNumberOfDistinctProducts(Map<Product,Integer> shoppingCartList) {
        if(shoppingCartList != null)
            return (int) shoppingCartList.keySet().stream().distinct().count();
        return 0;
    }

}

