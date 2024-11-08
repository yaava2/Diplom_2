package praktikum.user;

import org.apache.commons.lang3.RandomStringUtils;

public class User {
    private final String email;
    private final String password;
    private final String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User random() {
        return new User(RandomStringUtils.randomAlphanumeric(5, 15) + "@123.ru",
                RandomStringUtils.randomAlphanumeric(5, 10), RandomStringUtils.randomAlphanumeric(5, 10));
    }

    public static User withoutPassword() {
        return new User(RandomStringUtils.randomAlphanumeric(5, 15) + "@123.4",
                null, RandomStringUtils.randomAlphanumeric(5, 10));
    }

    public static User withoutEmail() {
        return new User(null,
                RandomStringUtils.randomAlphanumeric(5, 10), RandomStringUtils.randomAlphanumeric(5, 10));
    }

    public static User withoutName() {
        return new User(RandomStringUtils.randomAlphanumeric(5, 15) + "@123.4",
                RandomStringUtils.randomAlphanumeric(5, 10), null);
    }

    public static User withoutEmailPassword() {
        return new User(null,
                null, RandomStringUtils.randomAlphanumeric(5, 10));
    }

    public static User withoutEmailName() {
        return new User(null,
                "P@ssw0rd123", null);
    }

    public static User withoutPasswordName() {
        return new User(RandomStringUtils.randomAlphanumeric(5, 15) + "@123.4",
                null, null);
    }

    public static User withoutEmailPasswordName() {
        return new User(null,
                null, null);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
