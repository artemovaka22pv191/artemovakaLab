package tech.reliab.course.artemovaka.bank.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class User extends Person {
    private String addressJob;
    private double monthIncome;
    private Bank bank;
    private CreditAccount creditAccount;
    private PaymentAccount paymentAccount;
    private int creditRating;

    public User() {
        super();
    }

    public User(int id, String name, LocalDate dateBirth, double monthIncome, String addressJob, Bank bank) {
        super(id, name, dateBirth);
        this.addressJob = addressJob;
        this.monthIncome = monthIncome;
        this.bank = bank;
        creditRating = 0;
    }

    public User(User user) {
        super(user.getId(), user.getName(), user.getDateBirth());
        this.addressJob = user.getAddressJob();
        this.monthIncome = user.getMonthIncome();
        this.bank = user.getBank();
        creditAccount = user.getCreditAccount();
        paymentAccount = user.getPaymentAccount();
        creditRating = user.getCreditRating();
    }

    @Override
    public String toString() {

        return "ID пользователя: " + id + "\n" +
                "Имя пользователя: " + name + " \n" +
                "Дата рождения: " + dateBirth.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n" +
                "Работа: " + addressJob + "\n" +
                "Ежемесячный доход: " + String.format("%.4f", monthIncome) + "\n" +
                "Банк: " + (bank != null ? bank.getName() : "") + "\n" +
                "ID кредитного аккаунта: " + (creditAccount != null ? creditAccount.getId() : "") + "\n" +
                "ID платежного аккаунта: " + (paymentAccount != null ? paymentAccount.getId() : "") + "\n" +
                "Кредитный рейтинг: " + creditRating + "\n";
    }

    public void setAddressJob(String address) {
        addressJob = address;
    }

    public String getAddressJob() {
        return addressJob;
    }

    public void setMonthIncome(double monthIncome) {
        this.monthIncome = monthIncome;
    }

    public double getMonthIncome() {
        return monthIncome;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Bank getBank() {
        return bank;
    }

    public void setCreditAccount(CreditAccount account) {
        creditAccount = account;
    }

    public CreditAccount getCreditAccount() {
        return creditAccount;
    }

    public void setPaymentAccount(PaymentAccount account) {
        paymentAccount = account;
    }

    public PaymentAccount getPaymentAccount() {
        return paymentAccount;
    }

    public void setCreditRating(int rating) {
        creditRating = rating;
    }

    public int getCreditRating() {
        return creditRating;
    }
}
