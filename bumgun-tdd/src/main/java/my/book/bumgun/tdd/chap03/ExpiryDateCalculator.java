package my.book.bumgun.tdd.chap03;

import java.time.LocalDate;
import java.time.YearMonth;

public class ExpiryDateCalculator {

    public LocalDate calculateExpiryDate(PayData payData) {
        int addMonth = payData.getPayAmount() == 100_000 ? 12 : payData.getPayAmount() / 10000;

        if (payData.getFirstBillingDate() != null) {
            return expiryDateUsingFirstBillingDate(payData, addMonth);
        }

        return payData.getBillingDate().plusMonths(addMonth);
    }

    private LocalDate expiryDateUsingFirstBillingDate(PayData payData, int addMonth) {
        LocalDate addedBillingDate = payData.getBillingDate().plusMonths(addMonth);
        if (isSameDayOfMonth(payData, addedBillingDate)) {
            final int dayLenOfCandiMon = lastDayOfMonth(addedBillingDate);
            int dayOfFirstBilling = payData.getFirstBillingDate().getDayOfMonth();
            if (dayLenOfCandiMon < dayOfFirstBilling) {
                return addedBillingDate.withDayOfMonth(dayLenOfCandiMon);
            }
            return addedBillingDate.withDayOfMonth(dayOfFirstBilling);
        } else {
            return addedBillingDate;
        }
    }

    private int lastDayOfMonth(LocalDate addedBillingDate) {
        return YearMonth.from(addedBillingDate).lengthOfMonth();
    }

    private boolean isSameDayOfMonth(PayData payData, LocalDate addedBillingDate) {
        return payData.getFirstBillingDate().getDayOfMonth() != addedBillingDate.getDayOfMonth();
    }
}
