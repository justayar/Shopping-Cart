package model.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.category.Category;
import javax.validation.constraints.NotNull;

/**
 * @author canemreayar
 */

@Data
@AllArgsConstructor
public class Product {



    @NotNull
    private String title;

    @NotNull
    private double price;

    @NotNull
    private Category category;

}
