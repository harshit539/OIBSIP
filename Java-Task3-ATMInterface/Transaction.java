public class Transaction {

    private String type;
    private double amount;
    private String details;

    public Transaction(String type, double amount, String details) {
        this.type = type;
        this.amount = amount;
        this.details = details;
    }

    public void display() {
        System.out.println(
                type + " | Amount: Rs." + amount + " | " + details
        );
    }
}