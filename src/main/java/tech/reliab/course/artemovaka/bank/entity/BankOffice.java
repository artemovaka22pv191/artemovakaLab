package tech.reliab.course.artemovaka.bank.entity;

public class BankOffice {
    private int id;
    private String name;
    private String address;
    private Bank bank;
    private boolean isWorking;
    private boolean isInstallAtm;
    private int countAtm;
    private boolean isGiveCredit;
    private boolean isGiveMoney;
    private boolean isPayInMoney;
    private double money;
    private double rentPrice;
    private int countEmployee;

    public BankOffice() {
    }

    public BankOffice(int id, String name, String address, Bank bank, boolean isWorking, boolean isInstallAtm,
                      boolean isGiveCredit, boolean isGiveMoney, boolean isPayInMoney, double money,
                      double rentPrice) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.bank = bank;
        this.isWorking = isWorking;
        this.isInstallAtm = isInstallAtm;
        this.countAtm = 0;
        this.isGiveCredit = isGiveCredit;
        this.isGiveMoney = isGiveMoney;
        this.isPayInMoney = isPayInMoney;
        this.money = money;
        this.rentPrice = rentPrice;
        this.countEmployee = 0;
    }

    public BankOffice(BankOffice bankOffice) {
        this.id = bankOffice.getId();
        this.name = bankOffice.getName();
        this.address = bankOffice.getAddress();
        this.bank = bankOffice.getBank();
        this.isWorking = bankOffice.getIsWorking();
        this.isInstallAtm = bankOffice.getIsInstallAtm();
        this.countAtm = bankOffice.getCountAtm();
        this.isGiveCredit = bankOffice.getIsGiveCredit();
        this.isGiveMoney = bankOffice.getIsGiveMoney();
        this.money = bankOffice.getMoney();
        this.rentPrice = bankOffice.getRentPrice();
        this.isPayInMoney = bankOffice.getIsPayInMoney();
        this.countEmployee = bankOffice.getCountEmployee();
    }

    @Override
    public String toString() {
        return "ID офиса: " + id + "\n" +
                "Банковский офис: " + name + "\n" +
                "Банк: " + (bank != null ? bank.getName() : "") + " \n" +
                "Адрес: " + address + "\n" +
                "Работает: " + (isWorking ? "да" : "нет") + "\n" +
                "Можно устанавливать банкоматы: " + (isInstallAtm ? "да" : "нет") + "\n" +
                "Кол-во банкоматов: " + countAtm + "\n" +
                "Выдает кредиты: " + (isGiveCredit ? "да" : "нет") + "\n" +
                "Выдает деньги: " + (isGiveMoney ? "да" : "нет") + "\n" +
                "Можно вносить деньги: " + (isPayInMoney ? "да" : "нет") + "\n" +
                "Кол-во денег: " + String.format("%.4f", money) + "\n" +
                "Стоимость аренды: " + String.format("%.4f", rentPrice) + "\n";
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

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Bank getBank() {
        return bank;
    }

    public void setIsWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }

    public boolean getIsWorking() {
        return isWorking;
    }

    public void setIsInstallAtm(boolean isInstallAtm) {
        this.isInstallAtm = isInstallAtm;
    }

    public boolean getIsInstallAtm() {
        return isInstallAtm;
    }

    public void setCountAtm(int count) {
        this.countAtm = count;
    }

    public int getCountAtm() {
        return countAtm;
    }

    public void setIsGiveCredit(boolean isGiveCredit) {
        this.isGiveCredit = isGiveCredit;
    }

    public boolean getIsGiveCredit() {
        return isGiveCredit;
    }

    public void setIsGiveMoney(boolean isGiveMoney) {
        this.isGiveMoney = isGiveMoney;
    }

    public boolean getIsGiveMoney() {
        return isGiveMoney;
    }

    public void setIsPayInMoney(boolean isPayInMoney) {
        this.isPayInMoney = isPayInMoney;
    }

    public boolean getIsPayInMoney() {
        return isPayInMoney;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getMoney() {
        return money;
    }

    public void setRentPrice(double money) {
        this.rentPrice = money;
    }

    public double getRentPrice() {
        return this.rentPrice;
    }

    public void setCountEmployee(int count) {
        this.countEmployee = count;
    }

    public int getCountEmployee() {
        return this.countEmployee;
    }
}