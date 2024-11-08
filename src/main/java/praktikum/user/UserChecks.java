package praktikum.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import java.util.Map;
import java.util.Set;
import static java.net.HttpURLConnection.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;

public class UserChecks {
    @Step("пользователь создался успешно")
    public String checkCreated(ValidatableResponse response) {
        String accessToken = response
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .path("accessToken");

        assertNotEquals(0, accessToken);
        return accessToken;
    }

    @Step("залогинился")
    public void checkLoggedIn(ValidatableResponse loginResponse) {
        var body = loginResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .body().as(Map.class);

        assertEquals(true, body.get("success"));
        assertEquals(Set.of("success", "accessToken", "refreshToken", "user"), body.keySet());
    }

    @Step("попытка создания пользователя с повторяющимся логином")
    public void checkFailedCreateRepeat(ValidatableResponse response) {
        var body = response
                .assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .extract()
                .body().as(Map.class);

        assertEquals("User already exists", body.get("message"));
        assertEquals(false, body.get("success"));
        assertEquals(Set.of("success","message"), body.keySet());
    }

    @Step("попытка регистрации без логина, пароля или имени")
    public void checkFailedCreateUser(ValidatableResponse response) {
        var body = response
                .assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .extract()
                .body().as(Map.class);

        assertEquals("Email, password and name are required fields", body.get("message"));
        assertEquals(false, body.get("success"));
        assertEquals(Set.of("success","message"), body.keySet());
    }

    @Step("попытка залогиниться под пользователем с неправильной почтой или паролем")
        public void checkFailedLogin(ValidatableResponse response) {
            var body = response
                    .assertThat()
                    .statusCode(HTTP_UNAUTHORIZED)
                    .extract()
                    .body().as(Map.class);

        assertEquals("email or password are incorrect", body.get("message"));
        assertEquals(false, body.get("success"));
        assertEquals(Set.of("success","message"), body.keySet());
    }

    @Step("редактирование пользователя")
    public void checkEditUser(ValidatableResponse response) {
        var body = response
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .body().as(Map.class);

        assertEquals(true, body.get("success"));
        assertEquals(Set.of("success","user"), body.keySet());
    }

    @Step("попытка редактирования пользователя без авторизации")
    public void checkFailedLoginWithoutAuthorization(ValidatableResponse response) {
        var body = response
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .extract()
                .body().as(Map.class);

        assertEquals("You should be authorised", body.get("message"));
        assertEquals(false, body.get("success"));
        assertEquals(Set.of("success","message"), body.keySet());
    }

    public void deleted( ValidatableResponse response) {
    }
}
