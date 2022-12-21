package tech.reliab.course.artemovaka.bank.entity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreditAccount extends Account {
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private int countMonth;
    private double money;
    private double monthPay;
    private double interestRate;
    private Employee employee;
    private PaymentAccount paymentAccount;
    private double remainingSum;

    public CreditAccount() {
        super();
    }

    public CreditAccount(int id, User user, Bank bank, LocalDate dateStart, int countMonth, double money,
                         Employee employee, PaymentAccount paymentAccount) {
        super(id, user, bank);
        this.dateStart = dateStart;
        this.dateEnd = dateStart.plusMonths(countMonth);
        this.countMonth = countMonth;
        this.money = money;
        this.monthPay = money / countMonth + (money / 100 * bank.getInterestRate()) / 12;
        this.employee = employee;
        this.paymentAccount = paymentAccount;
        this.interestRate = bank.getInterestRate();
        this.remainingSum = this.money;
    }

    public CreditAccount(CreditAccount creditAccount) {
        super(creditAccount.getId(), creditAccount.getUser(), creditAccount.getBank());
        this.dateStart = creditAccount.getDateStart();
        this.dateEnd = creditAccount.getDateStart().plusMonths(creditAccount.getCountMonth());
        this.countMonth = creditAccount.getCountMonth();
        this.money = creditAccount.getMoney();
        this.monthPay = creditAccount.getMonthPay() + (creditAccount.getMoney() / 100 * creditAccount.getBank().getInterestRate()) / 12;
        this.employee = creditAccount.getEmployee();
        this.paymentAccount = creditAccount.getPaymentAccount();
        this.interestRate = creditAccount.getInterestRate();
        this.remainingSum = creditAccount.getRemainingSum();
        this.interestRate = creditAccount.getBank().getInterestRate();
    }

    @Override
    public String toString() {
        SimpleDateFormat fmt = new SimpleDateFormat("dd.MMM.yyyy");

        return "ID кредитного аккаунта: " + id + "\n" +
                "Банк: " + (bank != null ? bank.getName() : "") + " \n" +
                "Владелец: " + (user != null ? user.getName() : "") + "\n" +
                "Суммма кредита: " + String.format("%.4f", money) + "\n" +
                "Процентная ставка: " + interestRate + "\n" +
                "Дата начала кредита: " + (dateStart != null ? dateStart.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) : "") + "\n" +
                "Дата окончания кредита: " + (dateEnd != null ? dateEnd.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) : "") + "\n" +
                "Кол-во месяцев: " + countMonth + "\n" +
                "Ежемесячная выплата: " + String.format("%.4f", monthPay) + "\n" +
                "Работник: " + (employee != null ? employee.getName() : "") + "\n" +
                "ID платежного аккаунта: " + (paymentAccount != null ? paymentAccount.getId() : "") + "\n";
    }

    public void setDateStart(LocalDate date) {
        dateStart = date;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateEnd(LocalDate date) {
        dateEnd = date;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setCountMonth(int count) {
        this.countMonth = count;
    }

    public int getCountMonth() {
        return countMonth;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getMoney() {
        return money;
    }

    public void setMonthPay(double monthPay) {
        this.monthPay = monthPay;
    }

    public double getMonthPay() {
        return monthPay;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setPaymentAccount(PaymentAccount paymentAccount) {
        this.paymentAccount = paymentAccount;
    }

    public PaymentAccount getPaymentAccount() {
        return paymentAccount;
    }

    public void setRemainingSum(double sum) {
        remainingSum = sum;
    }

    public double getRemainingSum() {
        return remainingSum;
    }
}
