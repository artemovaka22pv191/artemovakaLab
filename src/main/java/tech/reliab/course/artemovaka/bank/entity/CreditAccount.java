package tech.reliab.course.artemovaka.bank.entity;

import tech.reliab.course.artemovaka.bank.entity.jsonClasses.JsonCreditAccount;
import tech.reliab.course.artemovaka.bank.service.impl.BankServiceImpl;
import tech.reliab.course.artemovaka.bank.service.impl.EmployeeServiceImpl;
import tech.reliab.course.artemovaka.bank.service.impl.PaymentAccountServiceImpl;
import tech.reliab.course.artemovaka.bank.service.impl.UserServiceImpl;

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
        this.dateEnd = null;
        this.countMonth = countMonth;
        this.money = money;
        this.monthPay = 0.0;
        this.employee = employee;
        this.paymentAccount = paymentAccount;
        this.interestRate = 0;
        this.remainingSum = this.money;
    }

    public CreditAccount(CreditAccount creditAccount) {
        super(creditAccount.getId(), creditAccount.getUser(), creditAccount.getBank());
        this.dateStart = creditAccount.getDateStart();
        this.dateEnd = creditAccount.getDateEnd();
        this.countMonth = creditAccount.getCountMonth();
        this.money = creditAccount.getMoney();
        this.monthPay = creditAccount.getMonthPay();
        this.employee = creditAccount.getEmployee();
        this.paymentAccount = creditAccount.getPaymentAccount();
        this.interestRate = creditAccount.getInterestRate();
        this.remainingSum = creditAccount.getRemainingSum();
    }

    public CreditAccount(JsonCreditAccount jsonCreditAcc) {
        this.id = (jsonCreditAcc.getId());
        this.bank = BankServiceImpl.getInstance().getBankById(jsonCreditAcc.getBankID());
        this.user = UserServiceImpl.getInstance().getUserById(jsonCreditAcc.getUserID());
        this.paymentAccount = PaymentAccountServiceImpl.getInstance().getPaymentAccountById(jsonCreditAcc.getPayAccID());
        this.employee = EmployeeServiceImpl.getInstance().getEmployeeById(jsonCreditAcc.getEmployeeID());
        if(jsonCreditAcc.getStartDate() != null){this.dateStart = LocalDate.parse(jsonCreditAcc.getStartDate());}
        if(jsonCreditAcc.getEndDate() != null){this.dateEnd = LocalDate.parse(jsonCreditAcc.getEndDate());}
        this.countMonth = jsonCreditAcc.getCountMonth();
        this.money = jsonCreditAcc.getMoney();
        this.monthPay = jsonCreditAcc.getCountMonth();
        this.interestRate = jsonCreditAcc.getInterestRate();
    }

    @Override
    public String toString() {
        SimpleDateFormat fmt = new SimpleDateFormat("dd.MMM.yyyy");

        return "ID кредитного аккаунта: " + id + "\n" +
                "Банк: " + (bank != null ? bank.getName() : "") + " \n" +
                "Владелец: " + (user != null ? user.getName() : "") + "\n" +
                "Суммма кредита: " + String.format("%.4f", money) + "\n" +
                "Дата начала кредита: " + (dateStart != null ? dateStart.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) : "") + "\n" +
                "Дата окончания кредита: " + (dateEnd != null ? dateEnd.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) : "") + "\n" +
                "Кол-во месяцев: " + countMonth + "\n" +
                "Ежемесячная выплата: " + String.format("%.4f", monthPay) + "\n" +
                "Процентная ставка: " + String.format("%.2f", interestRate) + "\n" +
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
