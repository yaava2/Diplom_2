package praktikum.order;

import org.apache.commons.lang3.RandomStringUtils;

public class Order {
    private final String[] ingredients;

    public Order(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public static Order random() {
        return new Order(new String[]{RandomStringUtils.randomAlphanumeric(20, 30)});
    }
}
