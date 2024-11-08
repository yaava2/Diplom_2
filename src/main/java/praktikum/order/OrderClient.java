package praktikum.order;

import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.qameta.allure.model.Parameter.Mode.HIDDEN;

public class OrderClient extends praktikum.Client{
    private static final String ORDERS_PATH = "/orders";

    @Step("создать заказ без авторизации и с ингридиентами")
    public ValidatableResponse createOrderWithoutAuthorizationWithIngredients(Order order) {
        return spec()
                .body(order)
                .when()
                .post(ORDERS_PATH)
                .then().log().all();
    }

    @Step("создать заказ c авторизацией и с ингридиентами")
    public ValidatableResponse createOrderWithAuthorizationWithIngredients(@Param(mode=HIDDEN) String accessToken, Order order) {
        return spec()
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDERS_PATH)
                .then().log().all();
    }

    @Step("запросить список ингредиентов")
    public ValidatableResponse getListOfIngredients() {
        return spec()
                .when()
                .get("/ingredients")
                .then().log().all();
    }

    @Step("попытка создать заказ c авторизацией и без ингридиентов")
    public ValidatableResponse createOrderWithAuthorizationWithoutIngredients(@Param(mode=HIDDEN) String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .when()
                .post(ORDERS_PATH)
                .then().log().all();
    }

    @Step("попытка создать заказ без авторизации и без ингридиентов")
    public ValidatableResponse createOrderWithoutAuthorizationWithIngredients() {
        return spec()
                .when()
                .post(ORDERS_PATH)
                .then().log().all();
    }

    @Step("запросить список заказов  пользователя с авторизацией")
    public ValidatableResponse getListOfOrdersWithAuthorization(@Param(mode=HIDDEN) String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .when()
                .get(ORDERS_PATH)
                .then().log().all();
    }

    @Step("запросить список заказов  пользователя без авторизации")
    public ValidatableResponse getListOfOrdersWithoutAuthorization() {
        return spec()
                .when()
                .get(ORDERS_PATH)
                .then().log().all();
    }


}
