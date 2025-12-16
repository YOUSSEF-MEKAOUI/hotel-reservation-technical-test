package hotel.entities;


public class User {
    private final int userId;
    private int balance;

    public User(int userId, int balance) {
        if (balance < 0) throw new IllegalArgumentException("Balance cannot be negative");
        this.userId = userId;
        this.balance = balance;
    }

    public int userId() {
        return userId;
    }

    public int balance() {
        return balance;
    }

    public void debit(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (amount > balance) throw new IllegalArgumentException("Insufficient balance");
        balance -= amount;
    }

    @Override
    public String toString() {
        return "User[id=" + userId + ", balance=" + balance + "]";
    }
}

