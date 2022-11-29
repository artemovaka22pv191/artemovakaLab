package tech.reliab.course.artemovaka.bank.entity;

import tech.reliab.course.artemovaka.bank.utils.Status;

public class BankAtm {
    private int id;
    private String name;
    private String address;
    private Status status;
    private Bank bank;
    private BankOffice bankOffice;
    private Employee employee;
    private boolean isGiveMoney;
    private boolean isPayInMoney;
    private double money;
    private double servicePrice;

    public BankAtm() {}

    public BankAtm(int id, String name, Status status, boolean isGiveMoney, boolean isPayInMoney, double money,
                   double servicePrice, BankOffice bankOffice, Bank bank, Employee employee) {
        this.id = id;
        this.name = name;
        this.address = "";
        this.status = status;
        this.isGiveMoney = isGiveMoney;
        this.isPayInMoney = isPayInMoney;
        this.money = money;
        this.servicePrice = servicePrice;
        this.bankOffice = bankOffice;
        this.bank = bank;
        this.employee = employee;
    }

    public BankAtm(BankAtm bankAtm) {
        this.id = bankAtm.getId();
        this.name = bankAtm.getName();
        this.address = bankAtm.getAddress();
        this.status = bankAtm.getStatus();
        this.bank = bankAtm.getBank();
        this.bankOffice = bankAtm.getBankOffice();
        this.employee = bankAtm.getEmployee();
        this.isGiveMoney = bankAtm.getIsGiveMoney();
        this.isPayInMoney = bankAtm.getIsPayInMoney();
        this.money = bankAtm.getMoney();
        this.servicePrice = bankAtm.getServicePrice();
    }

    @Override
    public String toString() {
        return "ID банкомата: " + id + "\n" +
                "Банкомат: " + name + " \n" +
                "Адрес: " + address + "\n" +
                "Статус: " + status.getName() + "\n" +
                "Банк: " + (bank != null ? bank.getName() : "") + "\n" +
                "Офис: " + (bankOffice != null ? bankOffice.getName() : "") + "\n" +
                "Работник: " + (employee != null ? employee.getName() : "") + "\n" +
                "Выдает деньги: " + (isGiveMoney ? "да" : "нет") + "\n" +
                "Можно положить деньги: " + (isPayInMoney ? "да" : "нет") + "\n" +
                "Количество денег: " + String.format("%.4f", money) + "\n" +
                "Стоимость обслуживания: " + String.format("%.4f", servicePrice) + "\n";
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBankOffice(BankOffice bankOffice) {
        this.bankOffice = bankOffice;
    }

    public BankOffice getBankOffice() {
        return bankOffice;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setIsGiveMoney(boolean isGiveMoney) {
        this.isGiveMoney = isGiveMoney;
    }

    public Boolean getIsGiveMoney() {
        return isGiveMoney;
    }

    public void setIsPayInMoney(boolean isPayInMoney) {
        this.isPayInMoney = isPayInMoney;
    }

    public Boolean getIsPayInMoney() {
        return isPayInMoney;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getMoney() {
        return money;
    }

    public void setServicePrice(double money) {
        this.servicePrice = money;
    }

    public double getServicePrice() {
        return servicePrice;
    }
}
