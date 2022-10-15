package ca.cmpt213.asn4.bank;

public class SavingsAccount extends BankAccount {
    boolean account_state;
    /*
    @par double number of current balance money
    @uses to check if this account is active or inactive
     */
    public void check_state (double balance_money){
        if (balance_money >= 25)
            account_state = true;
        else
            account_state = false;
    }
    /*
    @par double number of current balance money and double number of interest rate
    @uses call the super class and create the bank account.
     */
    public SavingsAccount(double money, double rate) throws IllegalArgumentException {
        super(money, rate);
        check_state(money);
    }
    /*
    @par double number of withdraw money
    @uses withdraw the money and check the state of account
     */
    @Override
    public void withdraw (double withdraw_amount){
        if(account_state){
            super.withdraw(withdraw_amount);
            check_state(super.balance);
        }
        else
            System.out.println(" You account is inactive !");
    }
    /*
    @par double number of deposit money
    @uses deposit the money and check the state of account
     */
    @Override
    public void deposit (double deposit_amount){
        if(account_state){
            super.deposit(deposit_amount);
            check_state(super.balance);
        }
        else
            System.out.println(" You account is inactive !");
    }
    /*
    @uses to calculate the money after few months and check if we need service charge.
     */
    public void monthlyProcess (){
        if (super.withdraw_money > 4){
            super.service_charge = super.withdraw_money-4;
        }
        super.monthlyProcess();
        check_state(super.balance);
    }

}
