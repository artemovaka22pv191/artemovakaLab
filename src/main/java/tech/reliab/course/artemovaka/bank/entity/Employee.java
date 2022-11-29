package tech.reliab.course.artemovaka.bank.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Employee extends Person {
    private String jobTitle;
    private Bank bank;
    private boolean isRemoteWork;
    private BankOffice office;
    private boolean isGiveCredit;
    private double salary;

    public Employee() {
        super();
    }

    public Employee(int id, String name, LocalDate dateBirth, String jobTitle, boolean isRemoteWork,
                    BankOffice bankOffice, boolean isGiveCredit, double salary) {
        super(id, name, dateBirth);
        this.jobTitle = jobTitle;
        this.isRemoteWork = isRemoteWork;
        this.office = bankOffice;
        this.isGiveCredit = isGiveCredit;
        this.salary = salary;
    }

    public Employee(Employee employee) {
        super(employee.getId(), employee.getName(), employee.getDateBirth());
        this.jobTitle = employee.getJobTitle();
        this.bank = employee.getBank();
        this.isRemoteWork = employee.getIsRemoteWork();
        this.office = employee.getOffice();
        this.isGiveCredit = employee.getIsGiveCredit();
        this.salary = employee.getSalary();
    }

    @Override
    public String toString() {

        return "ID работника: " + id + "\n" +
                "Имя работника: " + name + " \n" +
                "Дата рождения: " + dateBirth.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n" +
                "Должность: " + jobTitle + "\n" +
                "Банк: " + (bank != null ? bank.getName() : "") + "\n" +
                "Офис: " + (office != null ? office.getName() : "") + "\n" +
                "Работает удаленно: " + (isRemoteWork ? "да" : "нет") + "\n" +
                "Выдает кредиты: " + (isGiveCredit ? "да" : "нет") + "\n" +
                "Зарплата: " + String.format("%.4f", salary) + "\n";
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Bank getBank() {
        return bank;
    }

    public void setIsRemoteWork(boolean isRemoteWork) {
        this.isRemoteWork = isRemoteWork;
    }

    public boolean getIsRemoteWork() {
        return isRemoteWork;
    }

    public void setOffice(BankOffice office) {
        this.office = office;
    }

    public BankOffice getOffice() {
        return office;
    }

    public void setIsGiveCredit(boolean isGiveCredit) {
        this.isGiveCredit = isGiveCredit;
    }

    public boolean getIsGiveCredit() {
        return isGiveCredit;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }
}
