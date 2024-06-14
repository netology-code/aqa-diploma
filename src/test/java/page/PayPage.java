package page;

import com.codeborne.selenide.SelenideElement;
import data.CardInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PayPage {

    private final SelenideElement heading = $$("h3").find(exactText("Оплата по карте"));
    private final SelenideElement cardNumberField = $(byText("Номер карты")).parent().$("[class=\"input__control\"]");
    private final SelenideElement monthField = $(byText("Месяц")).parent().$("[class=\"input__control\"]");
    private final SelenideElement yearField = $(byText("Год")).parent().$("[class=\"input__control\"]");
    private final SelenideElement cardNameField = $(byText("Владелец")).parent().$("[class=\"input__control\"]");
    private final SelenideElement cvcField = $(byText("CVC/CVV")).parent().$("[class=\"input__control\"]");
    private final SelenideElement continueButton = $$("button").find(exactText("Продолжить"));
    private final SelenideElement operationApproved = $(byText("Операция одобрена Банком.")).parent().$("[class=\"notification__content\"]");
    private final SelenideElement error = $(byText("Ошибка! Банк отказал в проведении операции.")).parent().$("[class=\"notification__content\"]");
    private final SelenideElement invalidFormat = $(byText("Неверный формат"));
    private final SelenideElement invalidDataCard = $(byText("Неверно указан срок действия карты"));
    private final SelenideElement cardExpired = $(byText("Истёк срок действия карты"));
    private final SelenideElement invalidMassege = $(".input__sub");

    public PayPage() {heading.shouldBe(visible);}

    public void completedForm(CardInfo card) {
        cardNumberField.setValue(card.getNumber());
        monthField.setValue(card.getMonth());
        yearField.setValue(card.getYear());
        cardNameField.setValue(card.getName());
        cvcField.setValue(card.getCvc());
        continueButton.click();
    }

    public void expectationOperationApproved() {
        operationApproved.shouldBe(visible, Duration.ofSeconds(10));
    }

    public void expectationError() {
        error.shouldBe(visible, Duration.ofSeconds(10));
    }

    public void expectationInvalidFormat() {
        invalidFormat.shouldBe(visible);
    }

    public void expectationInvalidDataCard() {
        invalidDataCard.shouldBe(visible);
    }

    public void expectationCardExpired() {
        cardExpired.shouldBe(visible);
    }

    public String getInvalidText() {
        return invalidMassege.getText();
    }
}
