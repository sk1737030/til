package my.book.bumgun.tdd.chap07;

public class StubCardNumberValidator extends CardNumberValidator {

    private String invalidNo;
    private String theftNo;


    public StubCardNumberValidator() {
    }

    @Override
    public CardValidity validate(String cardNumber) {
        if (invalidNo != null && invalidNo.equals(cardNumber)) {
            return CardValidity.INVALID;
        }
        if (theftNo != null && theftNo.equals(cardNumber)) {
            return CardValidity.THEFT;
        }

        return CardValidity.VALID;
    }

    public void setInvalidNo(String s) {
        this.invalidNo = s;
    }

    public void setTheftNo(String theftNo) {
        this.theftNo = theftNo;
    }
}
