package data;

import lombok.Value;

@Value
public class CardInfo {
    private String number;
    private String month;
    private String year;
    private String name;
    private String cvc;
}
