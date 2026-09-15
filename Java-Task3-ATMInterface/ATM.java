import java.util.ArrayList;
import java.util.Scanner;

public class ATM {

    private Bank bank;
    private Scanner sc;
    public ATM(Bank bank) {
        this.bank = bank;
        this.sc = new Scanner(System.in);
    }

    public void start() {

        System.out.println("=================================");
        System.out.println("          ATM INTERFACE");
        System.out.println("=================================");

        Account currentAccount = login();

        if (currentAccount == null) {
            System.out.println("Access Denied!");
            return;
        }

        System.out.println("\nLogin Successful!");
        System.out.println("Welcome, " + currentAccount.getUserId());

        showMenu(currentAccount);
    }

    private Account login() {

        int attempts = 0;

        while (attempts < 3) {

            System.out.print("\nEnter User ID: ");
            String userId = sc.next();

            System.out.print("Enter PIN: ");
            int pin = sc.nextInt();

            Account account = bank.authenticate(userId, pin);

            if (account != null) {
                return account;
            }

            attempts++;

            System.out.println("Invalid User ID or PIN.");
            System.out.println("Attempts remaining: " + (3 - attempts));
        }

        return null;
    }

    private void showMenu(Account account) {

        int choice;

        do {

            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.println("===============================");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    account.printTransactionHistory();
                    break;

                case 2:
                    withdraw(account);
                    break;

                case 3:
                    deposit(account);
                    break;

                case 4:
                    transfer(account);
                    break;

                case 5:
                    System.out.println("Thank you for using the ATM.");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 5);
    }

    private void withdraw(Account account) {

        System.out.print("\nEnter withdrawal amount: Rs.");
        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid amount!");
            return;
        }

        if (account.withdraw(amount)) {

            account.addTransaction(
                    new Transaction(
                            "WITHDRAW",
                            amount,
                            "Cash withdrawn successfully"
                    )
            );

            System.out.println("Withdrawal successful.");
            System.out.println(
                    "Remaining Balance: Rs." + account.getBalance()
            );

        } else {

            System.out.println("Insufficient Funds");
        }
    }

    private void deposit(Account account) {

        System.out.print("\nEnter deposit amount: Rs.");
        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid amount!");
            return;
        }

        account.deposit(amount);

        account.addTransaction(
                new Transaction(
                        "DEPOSIT",
                        amount,
                        "Amount deposited successfully"
                )
        );

        System.out.println("Deposit successful.");
        System.out.println(
                "Current Balance: Rs." + account.getBalance()
        );
    }

    private void transfer(Account sender) {

        System.out.print("\nEnter recipient Account ID: ");
        String recipientId = sc.next();

        Account recipient = bank.findAccount(recipientId);

        if (recipient == null) {
            System.out.println("Recipient account not found.");
            return;
        }

        if (recipient == sender) {
            System.out.println("You cannot transfer money to your own account.");
            return;
        }

        System.out.print("Enter transfer amount: Rs.");
        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid amount!");
            return;
        }

        if (!sender.withdraw(amount)) {
            System.out.println("Insufficient Funds");
            return;
        }

        recipient.deposit(amount);

        sender.addTransaction(
                new Transaction(
                        "TRANSFER",
                        amount,
                        "Transferred to account " + recipientId
                )
        );

        recipient.addTransaction(
                new Transaction(
                        "RECEIVED",
                        amount,
                        "Received from account " + sender.getAccountId()
                )
        );

        System.out.println("Transfer successful.");
        System.out.println(
                "Remaining Balance: Rs." + sender.getBalance()
        );
    }
}