package model.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import model.category.Category;
import javax.validation.constraints.NotNull;

/**
 * @author canemreayar
 */

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Product {

    @NotNull
    private String title;

    @NotNull
    @EqualsAndHashCode.Exclude
    private double price;

    @NotNull
    private Category category;

}
