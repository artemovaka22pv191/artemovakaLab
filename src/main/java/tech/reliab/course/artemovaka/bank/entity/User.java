package tech.reliab.course.artemovaka.bank.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class User extends Person {
    private String addressJob;
    private double monthIncome;
    private List<Bank> banks = new ArrayList<>();
    private int countCreditAccount;
    private int countPaymentAccount;
    private int creditRating;

    public User() {
        super();
    }

    public User(int id, String name, LocalDate dateBirth, double monthIncome, String addressJob) {
        super(id, name, dateBirth);
        this.addressJob = addressJob;
        this.monthIncome = monthIncome;
        creditRating = 0;
        countPaymentAccount = 0;
        countCreditAccount = 0;
    }

    public User(User user) {
        super(user.getId(), user.getName(), user.getDateBirth());
        this.addressJob = user.getAddressJob();
        this.monthIncome = user.getMonthIncome();
        this.banks = user.getBanks();
        countPaymentAccount = user.getCountPaymentAccount();
        countCreditAccount = user.getCountCreditAccount();
        creditRating = user.getCreditRating();
    }

    @Override
    public String toString() {

        return "ID пользователя: " + id + "\n" +
                "Имя пользователя: " + name + " \n" +
                "Дата рождения: " + dateBirth.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n" +
                "Работа: " + addressJob + "\n" +
                "Ежемесячный доход: " + String.format("%.4f", monthIncome) + "\n" +
                "Кол-во кредитных аккаунтов: " + countCreditAccount + "\n" +
                "Кол-во платежных аккаунтов: " + countPaymentAccount + "\n" +
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

    public List<Bank> getBanks() {
        return banks;
    }

    public void addBank(Bank bank) {
        this.banks.add(bank);
    }

    public void setCountCreditAccount(int count) {
        this.countCreditAccount = count;
    }

    public int getCountCreditAccount() {
        return countCreditAccount;
    }

    public void setCountPaymentAccount(int count) {
        this.countPaymentAccount = count;
    }

    public int getCountPaymentAccount() {
        return countPaymentAccount;
    }


    public void setCreditRating(int rating) {
        creditRating = rating;
    }

    public int getCreditRating() {
        return creditRating;
    }
}
