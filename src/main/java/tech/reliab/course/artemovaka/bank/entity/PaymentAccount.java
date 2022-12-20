package tech.reliab.course.artemovaka.bank.entity;

import tech.reliab.course.artemovaka.bank.entity.jsonClasses.JsonPayAccount;
import tech.reliab.course.artemovaka.bank.service.impl.BankServiceImpl;
import tech.reliab.course.artemovaka.bank.service.impl.UserServiceImpl;

public class PaymentAccount extends Account {
    private double money;

    public PaymentAccount() {
        super();
    }

    public PaymentAccount(int id, User user, Bank bank, double money) {
        super(id, user, bank);
        this.money = money;
    }

    public PaymentAccount(PaymentAccount paymentAccount) {
        super(paymentAccount.getId(), paymentAccount.getUser(), paymentAccount.getBank());
        this.money = paymentAccount.getMoney();
    }

    public PaymentAccount(JsonPayAccount jsonPayAcc) {
        this.id = jsonPayAcc.getId();
        this.bank = BankServiceImpl.getInstance().getBankById(jsonPayAcc.getBankID());
        this.user = UserServiceImpl.getInstance().getUserById(jsonPayAcc.getUserID());
        this.money = jsonPayAcc.getMoney();
    }

    @Override
    public String toString() {
        return "ID платежного аккаунта: " + id + "\n" +
                "Банк: " + (bank != null ? bank.getName() : "") + " \n" +
                "Владелец: " + (user != null ? user.getName() : "") + "\n" +
                "Количество денег: " + String.format("%.4f", money) + "\n";
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getMoney() {
        return money;
    }
}
