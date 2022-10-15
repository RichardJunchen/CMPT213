package ca.cmpt213.asn4.bank;

public abstract class BankAccount
{

    protected double balance;
    protected int deposit_money;
    protected int withdraw_money;
    private double interest_rate;
    protected double service_charge;


    public BankAccount(double money, double rate){
        if (money <=0 ||rate <=0)
            throw new IllegalArgumentException(" Wrong input for initialization");
        balance = money;
        interest_rate = rate;
    }
    /*
    @par double number of deposit money
    @uses deposit the money
    @notice the deposit more than zero
     */
    public void deposit ( double deposit){
        if (deposit <0)
            throw new IllegalArgumentException(" Wrong input for deposit");
        balance += deposit;
        deposit_money += 1;
    }
    /*
    @par double number of withdraw money
    @uses withdraw the money
    @notice the deposit more than zero and can not more than balance money
     */
    public void withdraw ( double withdraw) {
        if (withdraw <0)
            throw new IllegalArgumentException(" Wrong input for withdraw ");
        if (withdraw > balance)
            throw new IllegalArgumentException(" Can not withdraw more money than balance ");
        balance -= withdraw;
        withdraw_money += 1;
    }
    /*
    @uses calculate the interest and change the balance
    @notice interest rate can not less than zero
     */
    public void calcInterest (){
        if (interest_rate <0)
            throw new IllegalArgumentException(" Interest rate can not be negative");
        double monthly_rate = interest_rate/12;
        double monthly_interest = balance * monthly_rate;
        balance +=monthly_interest;
    }
    /*
    @uses call calInterest and reset the counting variable to zero
     */
    public void monthlyProcess (){
        balance -= service_charge;
        calcInterest();
        withdraw_money = 0;
        deposit_money = 0;
        service_charge = 0;
    }
}
