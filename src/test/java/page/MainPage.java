package page;


import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {

    private final SelenideElement heading = $$("h2").find(exactText("Путешествие дня"));
    private final SelenideElement payButton = $$("button").find(exactText("Купить"));
    private final SelenideElement creditButton = $$("button").find(exactText("Купить в кредит"));

    public MainPage() {
        heading.shouldBe(visible);
    }

    public void openBuyPage() {
        payButton.click();
        new PayPage();
    }

    public void openCreditPage() {
        creditButton.click();
        new CreditPage();
    }
}
