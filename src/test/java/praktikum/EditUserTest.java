package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.user.User;
import praktikum.user.UserChecks;
import praktikum.user.UserClient;

public class EditUserTest {
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
    @DisplayName("редактирование авторизованного пользователя. изменение имени и почты")
    public void editUserWithAuthorizationEmailName() {
        ValidatableResponse response = client.edit(accessToken, user.getEmail(), user.getName());
        check.checkEditUser(response);
    }

    @Test
    @DisplayName("редактирование авторизованного пользователя. изменение имени")
    public void editUserWithAuthorizationName() {
        ValidatableResponse response = client.editOnlyName(accessToken, user.getName());
        check.checkEditUser(response);
    }

    @Test
    @DisplayName("редактирование авторизованного пользователя. изменение почты")
    public void editUserWithAuthorizationEmail() {
        ValidatableResponse response = client.editOnlyEmail(accessToken, user.getEmail());
        check.checkEditUser(response);
    }

    @Test
    @DisplayName("попытка редактирования неавторизованного пользователя")
    public void cannotEditUserWithoutAuthorization() {
        ValidatableResponse response = client.edit("", "", "");
        check.checkFailedLoginWithoutAuthorization(response);
    }
}
