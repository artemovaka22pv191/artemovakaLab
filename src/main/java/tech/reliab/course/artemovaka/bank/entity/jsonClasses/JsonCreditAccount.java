package tech.reliab.course.artemovaka.bank.entity.jsonClasses;

import tech.reliab.course.artemovaka.bank.entity.*;

import java.time.LocalDate;

public class JsonCreditAccount {
    private Integer id;
    private Integer bankID;
    private Integer userID;
    private String dateStart;
    private String dateEnd;
    private Integer countMonth;
    private Double money;
    private Double monthPay;
    private Double interestRate;
    private Integer employeeId;
    private Integer paymentAccountId;


    public JsonCreditAccount(CreditAccount creditAcc) {
        this.id = creditAcc.getId();
        this.bankID = creditAcc.getBank().getId();
        this.userID = creditAcc.getUser().getId();
        this.paymentAccountId = creditAcc.getPaymentAccount().getId();
        this.employeeId = creditAcc.getEmployee().getId();
        if (creditAcc.getDateStart() != null) {
            this.dateStart = creditAcc.getDateStart().toString();
        } else {
            this.dateStart = "";
        }
        if (creditAcc.getDateEnd() != null) {
            this.dateEnd = creditAcc.getDateEnd().toString();
        } else {
            this.dateEnd = "";
        }
        this.countMonth = creditAcc.getCountMonth();
        this.money = creditAcc.getMoney();
        this.monthPay = creditAcc.getMonthPay();
        this.interestRate = creditAcc.getInterestRate();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBankID() {
        return bankID;
    }

    public void setBankID(Integer bankID) {
        this.bankID = bankID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getPayAccID() {
        return paymentAccountId;
    }

    public void setPayAccID(Integer payAccID) {
        this.paymentAccountId = payAccID;
    }

    public Integer getEmployeeID() {
        return employeeId;
    }

    public void setEmployeeID(Integer employeeID) {
        this.employeeId = employeeID;
    }

    public String getStartDate() {
        return dateStart;
    }

    public void setStartDate(String startDate) {
        this.dateStart = startDate;
    }

    public String getEndDate() {
        return dateEnd;
    }

    public void setEndDate(String endDate) {
        this.dateEnd = endDate;
    }

    public Integer getCountMonth() {
        return countMonth;
    }

    public void setEndDate(Integer countMonth) {
        this.countMonth = countMonth;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getMonthPay() {
        return monthPay;
    }

    public void setMonthPay(Double monthPay) {
        this.monthPay = monthPay;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }
}
