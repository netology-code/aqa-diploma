package data;

import com.github.javafaker.Faker;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DataHelper {

    private static String getApprovedNumCard() {
        return ("4444 4444 4444 4441");
    }

    private static String getDeclinedNumCard() {
        return ("4444 4444 4444 4442");
    }

    public static String getMonth(int shift) {
        return LocalDate.now().plusMonths(shift).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getYear(int shift) {
        return LocalDate.now().plusYears(shift).format(DateTimeFormatter.ofPattern("YY"));
    }
    public static String getName() {
        Faker faker = new Faker();
        return faker.name().firstName() + " " + faker.name().lastName();
    }
    public static String getCvc() {
        Faker faker = new Faker();
        return faker.number().digits(3);
    }

    public static CardInfo getApprovedCard() {
        return new CardInfo(getApprovedNumCard(), getMonth(0), getYear(0), getName(), getCvc());
    }

    public static CardInfo getDeclinedCard() {
        return new CardInfo(getDeclinedNumCard(), getMonth(0), getYear(0), getName(), getCvc());
    }

    public static CardInfo getNonExistentMonth() {
        return new CardInfo(getApprovedNumCard(), "15", getYear(0), getName(), getCvc());
    }

    public static CardInfo getOneSymbolIntMonth() {
        return new CardInfo(getApprovedNumCard(), "6", getYear(0), getName(), getCvc());
    }

    public static CardInfo getMinusOneMonth() {
        return new CardInfo(getDeclinedNumCard(), getMonth(-1), getYear(0), getName(), getCvc());
    }

    public static CardInfo getPastYear() {
        return new CardInfo(getApprovedNumCard(), getMonth(0), getYear(-1), getName(), getCvc());
    }

    public static CardInfo getNextYear() {
        return new CardInfo(getApprovedNumCard(), getMonth(0), getYear(10), getName(), getCvc());
    }

    public static CardInfo getOneSymbolYear() {
        return new CardInfo(getApprovedNumCard(), getMonth(0), "4", getName(), getCvc());
    }

    public static CardInfo getNameCyrillic() {
        return new CardInfo(getApprovedNumCard(), getMonth(0), getYear(0), "Павел", getCvc());
    }

    public static CardInfo getNameLat() {
        return new CardInfo(getApprovedNumCard(), getMonth(0), getYear(0), "Pavel", getCvc());
    }

    public static CardInfo getNameSpecialSymbol() {
        return new CardInfo(getApprovedNumCard(), getMonth(0), getYear(0), "`!@#$%^&*", getCvc());
    }

    public static CardInfo getNameNumbers() {
        return new CardInfo(getApprovedNumCard(), getMonth(0), getYear(0), "1234567890", getCvc());
    }

    public static CardInfo getNameRandomManySymbol() {
        return new CardInfo(getApprovedNumCard(), getMonth(0), getYear(0), RandomStringUtils.random(70), getCvc());
    }

    public static CardInfo getCardInvalidNumbers() {
        return new CardInfo(RandomStringUtils.randomNumeric(15), getMonth(0), getYear(0), getName(), getCvc());
    }

    public static CardInfo getCvcOneNumber() {
        return new CardInfo(getApprovedNumCard(), getMonth(0), getYear(0), getName(), "1");
    }

    public static CardInfo getEmptyForm() {
        return new CardInfo("", "", "", "", "");
    }
}
