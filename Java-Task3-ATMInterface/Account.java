import java.util.ArrayList;

public class Account {
    private String accountId;
    private String userId;
    private int pin;
    private double balance;

    private ArrayList<Transaction> transactions;

    public Account(String accountId, String userId, int pin, double balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public String getAccountId() {
        return accountId;
    }

    public String getUserId() {
        return userId;
    }

    public boolean checkPin(int enteredPin) {
        return pin == enteredPin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }

        return false;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void printTransactionHistory() {
        System.out.println("\n====== TRANSACTION HISTORY ======");
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction transaction : transactions) {
                transaction.display();
            }
        }
        System.out.println("=================================");
    }
}