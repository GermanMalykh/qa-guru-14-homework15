package ru.sberlogistics.data;

public class TestData {
    public static final String
            remote = System.getProperty("remote"),
            browserName = System.getProperty("browser_name", "chrome"),
            browserVersion = System.getProperty("browser_version"),
            browserSize = System.getProperty("browser_size", "1920x1080"),
            LOGIN_REMOTE = "user1",
            PASSWORD_REMOTE = "1234",
            baseUrl = "https://sberlogistics.ru/";
}
