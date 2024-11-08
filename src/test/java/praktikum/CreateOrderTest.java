package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.order.Order;
import praktikum.order.OrderChecks;
import praktikum.order.OrderClient;
import praktikum.user.User;
import praktikum.user.UserChecks;
import praktikum.user.UserClient;

public class CreateOrderTest {
    private UserClient client = new UserClient();
    private UserChecks check = new UserChecks();
    User user = User.random();
    String accessToken;

    private OrderClient orderClient = new OrderClient();
    private OrderChecks orderCheck = new OrderChecks();

    @Before
    public void createUser() {
        ValidatableResponse createResponse = client.createUser(user);
        accessToken = check.checkCreated(createResponse);
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            ValidatableResponse response = client.delete(accessToken);
            check.deleted(response);
        }
    }

    @Test
    @DisplayName("создание заказа авторизованного пользователя с ингредиентами")
    public void createOrdersWithAuthorizationWithIngredients() {
        ValidatableResponse response = orderClient.getListOfIngredients();
        var ids = orderCheck.getIngredientsIds(response);

        Order order = new Order(new String[]{ids.get(0), ids.get(1)});
        orderClient.createOrderWithAuthorizationWithIngredients(accessToken, order);
    }

    @Test
    @DisplayName("создание заказа авторизованного пользователя без ингредиентов")
    public void createOrdersWithAuthorizationWithoutIngredients() {
        ValidatableResponse response1 = orderClient.createOrderWithAuthorizationWithoutIngredients(accessToken);
        orderCheck.checkFailedCreateOrderWithoutIngredients(response1);
    }

    @Test
    @DisplayName("создание заказа неавторизованного пользователя с ингредиентами")
    public void createOrdersWithoutAuthorizationWithIngredients() {
        ValidatableResponse response = orderClient.getListOfIngredients();
        var ids = orderCheck.getIngredientsIds(response);

        Order order = new Order(new String[]{ids.get(0), ids.get(1)});
        orderClient.createOrderWithoutAuthorizationWithIngredients(order);
    }

    @Test
    @DisplayName("создание заказа неавторизованного пользователя без ингредиентов")
    public void createOrdersWithoutAuthorizationWithoutIngredients() {
        ValidatableResponse response1 = orderClient.createOrderWithoutAuthorizationWithIngredients();
        orderCheck.checkFailedCreateOrderWithoutIngredients(response1);
    }

    @Test
    @DisplayName("создание заказа неавторизованного пользователя с неправильным хэшом ингредиентов")
    public void createOrdersWithoutAuthorizationWithFalseHashIngredients() {
        Order order = Order.random();
        ValidatableResponse response1 = orderClient.createOrderWithoutAuthorizationWithIngredients(order);
        orderCheck.checkFailedCreateOrderWithFalseHashIngredients(response1);
    }

    @Test
    @DisplayName("получение списка заказов авторизованного пользователя")
    public void getListOfOrdersWithAuthorization() {
        ValidatableResponse response = orderClient.getListOfIngredients();
        var ids = orderCheck.getIngredientsIds(response);

        Order order = new Order(new String[]{ids.get(0), ids.get(1)});
        orderClient.createOrderWithAuthorizationWithIngredients(accessToken, order);

        ValidatableResponse response1 = orderClient.getListOfOrdersWithAuthorization(accessToken);
        orderCheck.checkGetListOfOrdersWithAuthorization(response1);
    }

    @Test
    @DisplayName("попытка получения списка заказов неавторизованного пользователя")
    public void getListOfOrdersWithoutAuthorization() {
        ValidatableResponse response1 = orderClient.getListOfOrdersWithoutAuthorization();
        orderCheck.checkGetListOfOrdersWithoutAuthorization(response1);
    }

}
