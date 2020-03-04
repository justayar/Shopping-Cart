package model.product;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import model.category.Category;

/**
 * @author canemreayar
 */

@Data
public class Product {

    @NotNull
    private String title;

    @NotNull
    private double price;

    @NotNull
    private Category category;

}
