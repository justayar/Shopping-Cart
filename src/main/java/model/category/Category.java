package model.category;


import lombok.Data;

/**
 * @author canemreayar
 */

@Data
public class Category implements Comparable<Category> {

    private String title;
    private Category parentCategory;

    @Override
    public int compareTo(Category comparedCategory) {
        return this.title.compareTo(comparedCategory.title);
    }
}
