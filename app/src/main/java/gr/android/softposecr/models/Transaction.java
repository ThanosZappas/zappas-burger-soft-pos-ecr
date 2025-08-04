package gr.android.softposecr.models;

public class Transaction {
    private final String transactionNumber;
    private final String amount;
    private final String dateTime;

    public Transaction(String transactionNumber, String amount, String dateTime) {
        this.transactionNumber = transactionNumber;
        this.amount = amount;
        this.dateTime = dateTime;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public String getAmount() {
        return amount;
    }

    public String getDateTime() {
        return dateTime;
    }
}
