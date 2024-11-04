package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import praktikum.user.User;
import praktikum.user.UserChecks;
import praktikum.user.UserClient;
import praktikum.user.UserCredentials;

public class CreateUserTest {
    private UserClient client = new UserClient();
    private UserChecks check = new UserChecks();

    String accessToken;

    @After
    public void deleteUser() {
        if ( accessToken != null ) {
            ValidatableResponse response = client.delete(accessToken);
            check.deleted(response);
        }
    }

    @Test
    @DisplayName("регистрация пользователя")
    public void createdUser() {
        var user = User.random();
        ValidatableResponse createResponse = client.createUser(user);
        accessToken = check.checkCreated(createResponse);

        var creds = UserCredentials.fromUser(user);
        ValidatableResponse loginResponse = client.login(creds);
        check.checkLoggedIn(loginResponse);
    }

    @Test
    @DisplayName("ошибка регистрации пользователя. повторяющийся логин")
    public void cannotCreateLoginRepeat() {
        var user = User.random();
        ValidatableResponse createResponse = client.createUser(user);
        accessToken = check.checkCreated(createResponse);

        var userLoginRepeat = user;
        ValidatableResponse createResponse1 = client.createUser(userLoginRepeat);
        check.checkFailedCreateRepeat(createResponse1);
    }

    @Test
    @DisplayName("ошибка регистрации пользователя. все поля пустые")
    public void cannotCreateWithoutEmailPasswordName() {
        var user = User.withoutEmailPasswordName();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkFailedCreateUser(createResponse);
    }

    @Test
    @DisplayName("ошибка регистрации пользователя. без почты")
    public void cannotCreateWithoutEmail() {
        var user = User.withoutEmail();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkFailedCreateUser(createResponse);
    }

    @Test
    @DisplayName("ошибка регистрации пользователя. без пароля")
    public void cannotCreateWithoutPassword() {
        var user = User.withoutPassword();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkFailedCreateUser(createResponse);
    }

    @Test
    @DisplayName("ошибка регистрации пользователя. без имени")
    public void cannotCreateWithoutName() {
        var user = User.withoutName();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkFailedCreateUser(createResponse);
    }

    @Test
    @DisplayName("ошибка регистрации пользователя. без почты и пароля")
    public void cannotCreateWithoutEmailPassword() {
        var user = User.withoutEmailPassword();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkFailedCreateUser(createResponse);
    }

    @Test
    @DisplayName("ошибка регистрации пользователя. без почты и имени")
    public void cannotCreateWithoutEmailName() {
        var user = User.withoutEmailName();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkFailedCreateUser(createResponse);
    }

    @Test
    @DisplayName("ошибка регистрации пользователя. без пароля и имени")
    public void cannotCreateWithoutPasswordName() {
        var user = User.withoutPasswordName();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkFailedCreateUser(createResponse);
    }

}
