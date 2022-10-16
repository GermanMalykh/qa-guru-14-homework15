package ru.sberlogistics.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.junit5.BrowserPerTestStrategyExtension;
import com.codeborne.selenide.logevents.SelenideLogger;
import ru.sberlogistics.data.TestData;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.remote.DesiredCapabilities;

@ExtendWith({BrowserPerTestStrategyExtension.class})
public class TestBase {

    @BeforeAll
    static void configure() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        DesiredCapabilities capabilities = new DesiredCapabilities();

        Configuration.browserCapabilities = capabilities;
        Configuration.baseUrl = TestData.baseUrl;
        Configuration.browser = TestData.browserName;
        Configuration.browserVersion = TestData.browserVersion;
        Configuration.browserSize = TestData.browserSize;

        if (TestData.remote == null || TestData.remote.equals("")) {
        } else {
            Configuration.remote = "https://"
                    + TestData.LOGIN_REMOTE + ":"
                    + TestData.PASSWORD_REMOTE + "@"
                    + TestData.remote;

            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", true);
        }

        if (TestData.browserVersion != null) {
            Configuration.browserVersion = TestData.browserVersion;
        }
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.getConsoleLogs();

        if (TestData.remote == null || TestData.remote.equals("")) {
        } else {
            Attach.addVideo();
        }
    }
}