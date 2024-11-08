package praktikum.user;

import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import java.util.Map;
import static io.qameta.allure.model.Parameter.Mode.HIDDEN;

public class UserClient extends praktikum.Client {
    private static final String USER_PATH = "/auth";

    @Step("создать пользователя")
    public ValidatableResponse createUser(User user) {
        return spec()
                .body(user)
                .when()
                .post(USER_PATH+"/register")
                .then().log().all();
    }

    @Step("залогиниться под пользователем")
    public ValidatableResponse login(UserCredentials creds) {
        return spec()
                .body(creds)
                .when()
                .post(USER_PATH + "/login")
                .then().log().all();
    }

    @Step("удалить пользователя")
    public ValidatableResponse delete (@Param(mode=HIDDEN) String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .when()
                .delete(USER_PATH + "/user")
                .then().log().all();
    }

    @Step("изменить данные пользователя. смена почты и имени")
    public ValidatableResponse edit(@Param(mode=HIDDEN) String accessToken, String email, String name) {
        return spec()
                .header("Authorization", accessToken)
                .body(Map.of("email", email, "name", name))
                .when()
                .patch(USER_PATH + "/user")
                .then().log().all();
    }

    @Step("изменить данные пользователя. смена почты")
    public ValidatableResponse editOnlyEmail(@Param(mode=HIDDEN) String accessToken, String email) {
        return spec()
                .header("Authorization", accessToken)
                .body(Map.of("email", email))
                .when()
                .patch(USER_PATH + "/user")
                .then().log().all();
    }

    @Step("изменить данные пользователяю смена имени")
    public ValidatableResponse editOnlyName(@Param(mode=HIDDEN) String accessToken, String name) {
        return spec()
                .header("Authorization", accessToken)
                .body(Map.of("name", name))
                .when()
                .patch(USER_PATH + "/user")
                .then().log().all();
    }


}
