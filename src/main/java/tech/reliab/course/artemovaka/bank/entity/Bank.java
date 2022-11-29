package tech.reliab.course.artemovaka.bank.entity;

public class Bank {
    private int id;
    private String name;
    private int countOffice;
    private int countAtm;
    private int countEmployee;
    private int countClient;
    private int rating;
    private double money;
    private double interestRate;

    public Bank() {}

    public Bank(int id, String name) {
        this.id = id;
        this.name = name;
        this.countOffice = 0;
        this.countAtm = 0;
        this.countEmployee = 0;
        this.countClient = 0;
    }

    public Bank(Bank bank) {
        this.id = bank.getId();
        this.name = bank.getName();
        this.countOffice = bank.getCountOffice();
        this.countAtm = bank.getCountAtm();
        this.countEmployee = bank.getCountEmployee();
        this.countClient = bank.getCountClient();
        this.rating = bank.getRating();
        this.money = bank.getMoney();
        this.interestRate = bank.getInterestRate();
    }

    @Override
    public String toString() {
        return "ID банка: " + id + "\n" +
                "Банк: " + name + " \n" +
                "Количество офисов: " + countOffice + "\n" +
                "Количество банкоматов: " + countAtm + "\n" +
                "Количество работников: " + countEmployee + "\n" +
                "Количество клиентов: " + countClient + "\n" +
                "Рейтинг: " + rating + "\n" +
                "Количество денег: " + String.format("%.4f", money) + "\n" +
                "Процентная ставка: " + String.format("%.2f", interestRate) + "\n";
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

    public void setCountOffice(int count) {
        this.countOffice = count;
    }

    public int getCountOffice() {
        return countOffice;
    }

    public void setCountAtm(int count) {
        this.countAtm = count;
    }

    public int getCountAtm() {
        return countAtm;
    }

    public void setCountEmployee(int count) {
        this.countEmployee = count;
    }

    public int getCountEmployee() {
        return countEmployee;
    }

    public void setCountClient(int count) {
        this.countClient = count;
    }

    public int getCountClient() {
        return countClient;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getMoney() {
        return money;
    }

    public void setInterestRate(double rate) {
        this.interestRate = rate;
    }

    public double getInterestRate() {
        return interestRate;
    }
}
