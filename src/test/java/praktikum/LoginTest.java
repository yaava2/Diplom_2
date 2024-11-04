package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.user.User;
import praktikum.user.UserChecks;
import praktikum.user.UserClient;
import praktikum.user.UserCredentials;

public class LoginTest {
    private UserClient client = new UserClient();
    private UserChecks check = new UserChecks();
    User user = User.random();
    String accessToken;

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
    @DisplayName("авторизация под существующим пользователем")
    public void loginUser() {
            var creds = UserCredentials.fromUser(user);
            ValidatableResponse loginResponse = client.login(creds);
            check.checkLoggedIn(loginResponse);
    }

    @Test
    @DisplayName("попытка авторизации с неверной почтой")
    public void cannotLoginUserIncorrectEmail() {
        var userFailed = User.withoutEmail();
        var creds = UserCredentials.fromUser(userFailed);
        ValidatableResponse loginResponse = client.login(creds);
        check.checkFailedLogin(loginResponse);
    }

    @Test
    @DisplayName("попытка авторизации с неверным паролем")
    public void cannotLoginUserIncorrectPassword() {
        var userFailed = User.withoutPassword();
        var creds = UserCredentials.fromUser(userFailed);
        ValidatableResponse loginResponse = client.login(creds);
        check.checkFailedLogin(loginResponse);
    }

}
