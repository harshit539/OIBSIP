import java.util.ArrayList;

public class Bank {

    private ArrayList<Account> accounts;

    public Bank() {
        accounts = new ArrayList<>();

        // Sample accounts
        accounts.add(new Account("ACC1001", "user1", 1234, 10000));
        accounts.add(new Account("ACC1002", "user2", 5678, 5000));
    }

    public Account authenticate(String userId, int pin) {

        for (Account account : accounts) {

            if (account.getUserId().equals(userId)
                    && account.checkPin(pin)) {

                return account;
            }
        }

        return null;
    }

    public Account findAccount(String accountId) {

        for (Account account : accounts) {

            if (account.getAccountId().equals(accountId)) {
                return account;
            }
        }

        return null;
    }
}