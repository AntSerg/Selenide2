package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.$;


public class CompletionFormTest {
    public String dataGenerator (int days) {
        if (days < 3) {
            days = 3;
        }
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    public void formSuccessfulCompleted () {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Самара");
        String myDate = dataGenerator(5);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(myDate);
        $("[data-test-id='name'] input").setValue("Пушкин Александр");
        $("[data-test-id='phone'] input").setValue("+79217990606");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + myDate));
    }

}
