package ru.sberlogistics.tests;

import com.codeborne.selenide.Condition;
import helpers.Attach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static ru.sberlogistics.data.TestData.baseUrl;
import static org.assertj.core.api.Assertions.assertThat;

public class WebTests extends TestBase {

    @DisplayName("Проверка ошибок в консоле")
    @Test
    void consoleShouldNotHaveErrorsTest() {
        step("Открываем главную страницу \"" + baseUrl + "\"", () -> {
            open(baseUrl);
        });
        step("Console logs should not contain text 'SEVERE'", () -> {
            String consoleLogs = Attach.getConsoleLogs();
            String errorText = "SEVERE";
            assertThat(consoleLogs).doesNotContain(errorText);
        });
    }

    @DisplayName("Проверка отображения логотипа")
    @Test
    void checkLogoVisibleTest() {
        step("Открываем главную страницу \"" + baseUrl + "\"", () -> {
            open(baseUrl);
        });
        step("Проверяем отображенпе логотипа на странице", () -> {
            $(".logo")
                    .shouldBe(Condition.visible);
        });
    }

    @DisplayName("Проверка пунктов навигационного меню")
    @ValueSource(strings = {"Отправить посылку", "Интернет-магазинам", "Самозанятым",
            "Корпоративным клиентам", "Стать партнером", "Новости"})
    @ParameterizedTest(name = "Пункт меню \"{0}\", отображается на сайте")
    void checkNavigationMenuTest(String testData) {
        step("Открываем главную страницу \"" + baseUrl + "\"", () -> {
            open(baseUrl);
        });
        step("Проверяем наличие пункта \"" + testData + "\" в навигационном меню", () -> {
            $(".nav")
                    .shouldHave(Condition.text(testData));
        });
    }

    @DisplayName("Проверка расчета доставки")
    @Test
    void calculateShipmentTest() {

        final String DepartureCity = "Архангельск",
                CityOfReceipt = "Мурманск";

        step("Открываем главную страницу \"" + baseUrl + "\"", () -> {
            open(baseUrl);
            executeJavaScript("$('.cookie-popup').remove()");
        });
        step("Указываем город из которого выполняем отправление посылки: \"" + DepartureCity + "\"", () -> {
            $("[placeholder='Адрес отправки']")
                    .shouldBe(Condition.visible)
                    .setValue(DepartureCity);
        });
        step("Выбираем в списке город, из которого выполняем отправление посылки: \"" + DepartureCity + "\"", () -> {
            $("[data-full='г. " + DepartureCity + ", Россия']")
                    .shouldHave(Condition.text(DepartureCity))
                    .click();
        });
        step("Указываем город в который доставляем посылку: \"" + CityOfReceipt + "\"", () -> {
            $("[placeholder='Адрес доставки']")
                    .shouldBe(Condition.visible)
                    .setValue(CityOfReceipt);
        });
        step("Выбираем в списке город, из которого выполняем отправление посылки: \"" + CityOfReceipt + "\"", () -> {
            $("[data-full='г. " + CityOfReceipt + ", Россия']")
                    .shouldHave(Condition.text(CityOfReceipt))
                    .click();
        });
        step("Выполянем рассчет", () -> {
            $(".submit")
                    .click();
        });
        step("Проверяем парамтеры доставки", () -> {
            $(".side-left")
                    .shouldHave(Condition.text("Параметры доставки"));
            $(".side-right")
                    .shouldHave(Condition.text("Стоимость доставки"));
            $(".price-button-summ")
                    .shouldNotBe(Condition.empty);
            $("[placeholder='Адрес отправки']")
                    .shouldBe(Condition.visible)
                    .shouldNotBe(Condition.empty);
            $("[placeholder='Адрес доставки']")
                    .shouldBe(Condition.visible)
                    .shouldNotBe(Condition.empty);
        });
    }

}
