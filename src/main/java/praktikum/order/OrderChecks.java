package praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static java.net.HttpURLConnection.*;
import static org.junit.Assert.assertEquals;

public class OrderChecks {
    @Step("заказ создан")
    public void checkCreateOrder(ValidatableResponse Response) {
        var body = Response
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .body().as(Map.class);

        assertEquals(true, body.get("success"));
        assertEquals(Set.of("success", "name", "order"), body.keySet());
    }

    @Step("получение id ингредиентов")
    public  List<String> getIngredientsIds(ValidatableResponse response) {
        return response
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .path("data._id");
    }

    @Step("попытка создать заказ без ингридиентов")
    public void checkFailedCreateOrderWithoutIngredients(ValidatableResponse Response) {
        var body = Response
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .extract()
                .body().as(Map.class);

        assertEquals("Ingredient ids must be provided", body.get("message"));
        assertEquals(false, body.get("success"));
        assertEquals(Set.of("success", "message"), body.keySet());
    }

    @Step("попытка создать заказ с неверным хешем ингридиентов")
    public void checkFailedCreateOrderWithFalseHashIngredients(ValidatableResponse Response) {
        var body = Response
                .assertThat()
                .statusCode(HTTP_INTERNAL_ERROR)
                .extract();

    }

    @Step("запросить список заказов  пользователя с авторизацией")
    public void checkGetListOfOrdersWithAuthorization(ValidatableResponse Response) {
        var body = Response
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .body().as(Map.class);

        assertEquals(true, body.get("success"));
        assertEquals(Set.of("success", "orders", "total", "totalToday"), body.keySet());
    }

    @Step("запросить список заказов  пользователя без авторизации")
    public void checkGetListOfOrdersWithoutAuthorization(ValidatableResponse Response) {
        var body = Response
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .extract()
                .body().as(Map.class);

        assertEquals("You should be authorised", body.get("message"));
        assertEquals(false, body.get("success"));
        assertEquals(Set.of("success", "message"), body.keySet());
    }


}
