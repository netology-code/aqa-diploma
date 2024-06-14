package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.CardInfo;
import data.DataHelper;
import data.SqlHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import page.MainPage;
import page.PayPage;

import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PayTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }
    @BeforeEach
    public void setUp() {
        String url = System.getProperty("sut.url");
        open(url);
    }
    @AfterEach
    public void deleteDB() {
        SqlHelper.deleteDataBase();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("successesCase")
    void successesCaseByCreditTest(String name, CardInfo parm, String expected) {
        var mainPage = new MainPage();
        mainPage.openBuyPage();
        var payment = new PayPage();
        payment.completedForm(parm);
        payment.expectationOperationApproved();
        assertEquals(expected, SqlHelper.getPayStatus());
    }

    static Stream<Object> successesCase() {
        return Stream.of(
                Arguments.of(
                        "SuccessCase",
                        DataHelper.getApprovedCard(),
                        "APPROVED"),
                Arguments.of(
                        "Ввод имени на кириллице",
                        DataHelper.getNameCyrillic(),
                        "APPROVED"));
    }

    @Test
    void failCaseByCreditTest() {
        var mainPage = new MainPage();
        mainPage.openBuyPage();
        var payment = new PayPage();
        payment.completedForm(DataHelper.getDeclinedCard());
        payment.expectationError();
        assertEquals("DECLINED", SqlHelper.getPayStatus());
    }

    @Test
    void зastYearTest() {
        var mainPage = new MainPage();
        mainPage.openBuyPage();
        var payment = new PayPage();
        payment.completedForm(DataHelper.getPastYear());
        payment.expectationCardExpired();
        assertEquals("Истёк срок действия карты", payment.getInvalidText());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidFormat")
    void invalidFormatTest (String name, CardInfo card, String expected) {
        var mainPage = new MainPage();
        mainPage.openBuyPage();
        var payment = new PayPage();
        payment.completedForm(card);
        payment.expectationInvalidFormat();
        assertEquals(expected, payment.getInvalidText());
    }

    static Stream<Object> invalidFormat() {
        return Stream.of(
                Arguments.of(
                        "Пустая форма",
                        DataHelper.getEmptyForm(),
                        "Неверный формат"),
                Arguments.of(
                        "Одна цифра в месяце",
                        DataHelper.getOneSymbolIntMonth(),
                        "Неверный формат"),
                Arguments.of(
                        "Одна цифра в году",
                        DataHelper.getOneSymbolYear(),
                        "Неверный формат"),
                Arguments.of(
                        "Ввод в поле спец-символов",
                        DataHelper.getNameSpecialSymbol(),
                        "Неверный формат"),
                Arguments.of(
                        "Ввод в поле Имя цифры",
                        DataHelper.getNameNumbers(),
                        "Неверный формат"),
                Arguments.of(
                        "Ввод в поле Имя >64 символов",
                        DataHelper.getNameRandomManySymbol(),
                        "Неверный формат"),
                Arguments.of(
                        "Ввод в поле Номер карты 15 цифр",
                        DataHelper.getCardInvalidNumbers(),
                        "Неверный формат"),
                Arguments.of(
                        "Ввод в поле CVC одну цифру",
                        DataHelper.getCvcOneNumber(),
                        "Неверный формат"));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("expirationDateIncorrect")
    void expirationDateIncorrectTest(String name, CardInfo card, String expected) {
        var mainPage = new MainPage();
        mainPage.openBuyPage();
        var payment = new PayPage();
        payment.completedForm(card);
        payment.expectationInvalidDataCard();
        assertEquals(expected, payment.getInvalidText());
    }

    static Stream<Object> expirationDateIncorrect() {
        return Stream.of(
                Arguments.of(
                        "Не существующий месяц",
                        DataHelper.getNonExistentMonth(),
                        "Неверно указан срок действия карты"),
                Arguments.of(
                        "Десять лет вперед",
                        DataHelper.getNextYear(),
                        "Неверно указан срок действия карты"),
                Arguments.of(
                        "Прошедший месяц",
                        DataHelper.getMinusOneMonth(),
                        "Неверно указан срок действия карты"));
    }
}
