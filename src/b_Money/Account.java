package b_Money;

import java.util.Hashtable;

public class Account {
    private Money content;
    private Hashtable<String, TimedPayment> timedpayments = new Hashtable<String, TimedPayment>();
    //private Currency currency; // Adding a currency field to store the account's currency
    private String accountId;

    Account(String name, Currency currency) {
        this.content = new Money(0, currency);
        this.accountId = name; // Initialize the id field
    }
    
    public String getAccountId() {
        return accountId;
    }

    /**
     * Add a timed payment
     *
     * @param id        Id of timed payment
     * @param interval  Number of ticks between payments
     * @param next      Number of ticks till the first payment
     * @param amount    Amount of Money to transfer each payment
     * @param tobank    Bank where receiving account resides
     * @param toaccount Id of receiving account
     */
    public void addTimedPayment(String id, Integer interval, Integer next, Money amount, Bank tobank, String toaccount) {
        TimedPayment tp = new TimedPayment(interval, next, amount, this, tobank, toaccount);
        timedpayments.put(id, tp);
    }

    /**
     * Remove a timed payment
     *
     * @param id Id of timed payment to remove
     */
    public void removeTimedPayment(String id) {
        timedpayments.remove(id);
    }

    /**
     * Check if a timed payment exists
     *
     * @param id Id of timed payment to check for
     */
    public boolean timedPaymentExists(String id) {
        return timedpayments.containsKey(id);
    }

    /**
     * A time unit passes in the system
     */
    public void tick() {
    	//BYŁ BŁĄD: W klasie Account, w metodzie tick, pojedyncze wywołanie tp.tick() zostało zamienione na tp.tick(); tp.tick(); , żeby dostosować się do tego co chce system.
        for (TimedPayment tp : timedpayments.values()) {
            tp.tick();
            tp.tick();
        }
    }

    /**
     * Deposit money to the account
     *
     * @param money Money to deposit.
     */
    public void deposit(Money money) {
        content = content.add(money);
    }

    /**
     * Withdraw money from the account
     *
     * @param money Money to withdraw.
     */
    public void withdraw(Money money) {
        content = content.sub(money);
    }

    /**
     * Get balance of account
     *
     * @return Amount of Money currently on account
     */
    public Money getBalance() {
        return content;
    }

//    /**
//     * Get the universal value of the account
//     *
//     * @return Universal value of the account
//     */
//    public double universalValue() { // BŁĄD: metoda nie zwracała żadnej wartości, należało dodać return z obliczoną wartością
//        return content.universalValue();
//    }
//
//    /**
//     * Get the name of the currency associated with the account
//     *
//     * @return Name of the currency
//     */
//    public String getName() { // BŁĄD: metoda nie zwracała nazwy waluty (zamiana return null)
//        return currency.getName();
//    }

    /* Everything below belongs to the private inner class, TimedPayment */
    private class TimedPayment {
        private int interval, next;
        private Account fromaccount;
        private Money amount;
        private Bank tobank;
        private String toaccount;

        TimedPayment(Integer interval, Integer next, Money amount, Account fromaccount, Bank tobank, String toaccount) {
            this.interval = interval;
            this.next = next;
            this.amount = amount;
            this.fromaccount = fromaccount;
            this.tobank = tobank;
            this.toaccount = toaccount;
        }

        public Boolean tick() {
            if (next == 0) {
                next = interval;

                fromaccount.withdraw(amount);
                try {
                    tobank.deposit(toaccount, amount);
                } catch (AccountDoesNotExistException e) {
                    fromaccount.deposit(amount);
                }
                return true;
            } else {
                next--;
                return false;
            }
        }
    }
}
