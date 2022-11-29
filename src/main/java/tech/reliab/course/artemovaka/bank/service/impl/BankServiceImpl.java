package tech.reliab.course.artemovaka.bank.service.impl;

import tech.reliab.course.artemovaka.bank.entity.*;
import tech.reliab.course.artemovaka.bank.service.BankService;

import java.time.LocalDate;
import java.util.Random;

public class BankServiceImpl implements BankService {
    @Override
    public Bank create(Bank bank) {
        if (bank != null) {
            Random random = new Random();
            bank.setRating(random.nextInt(100));
            bank.setMoney(random.nextDouble(1000000));
            calculateInterestRate(bank);
            return new Bank(bank);
        }

        return null;
    }

    @Override
    public void calculateInterestRate(Bank bank) {
        if (bank != null) {
            Random random = new Random();
            int rating = bank.getRating();
            var offset = random.nextDouble() * 5;
            if (rating < 10) {
                bank.setInterestRate(offset + 15);
            } else if (rating < 40) {
                bank.setInterestRate(offset + 10);
            } else if (rating < 80) {
                bank.setInterestRate(offset + 5);
            } else {
                bank.setInterestRate(offset);
            }
        }
    }

    @Override
    public void depositMoney(Bank bank, double sum) {
        if (bank != null) {
            bank.setMoney(bank.getMoney() + sum);
        }
    }

    @Override
    public void withdrawMoney(Bank bank, double sum) {
        if (bank != null) {
            if (bank.getMoney() >= sum) {
                bank.setMoney(bank.getMoney() - sum);
            } else {
                System.out.println("В банке " + bank.getName() + " недостаточно денег для выдачи\n");
            }
        }
    }

    @Override
    public void addClient(Bank bank, User user) {
        if ((bank != null) && (user != null)) {
            user.setBank(bank);
            bank.setCountClient(bank.getCountClient() + 1);
        }
    }

    @Override
    public void removeClient(Bank bank) {
        if (bank != null) {
            int countClient = bank.getCountClient();
            if (countClient == 0) {
                System.out.println("Ошибка! Кол-во клиентов в банке не может быть отрицательным числом");
            } else {
                bank.setCountClient(countClient - 1);
            }
        }
    }

    @Override
    public void addEmployee(Bank bank, Employee employee) {
        if ((bank != null) && (employee != null)) {
            employee.setBank(bank);
            bank.setCountEmployee(bank.getCountEmployee() + 1);
        }
    }

    @Override
    public void removeEmployee(Bank bank) {
        if (bank != null) {
            int countEmployee = bank.getCountEmployee();
            if (countEmployee == 0) {
                System.out.println("Ошибка! Кол-во работников в банке не может быть отрицательным числом");
            } else {
                bank.setCountClient(countEmployee - 1);
            }
        }
    }

    @Override
    public void addOffice(Bank bank, BankOffice bankOffice) {
        if ((bank != null) && (bankOffice != null)) {
            bank.setCountOffice(bank.getCountOffice() + 1);
        }
    }

    @Override
    public void removeOffice(Bank bank, BankOffice bankOffice) {
        if ((bank != null) && (bankOffice != null)) {
            int countOffice = bank.getCountOffice();
            if (countOffice == 0) {
                System.out.println("Ошибка! Кол-во офисов в банке не может быть отрицательным числом");
            } else {
                bank.setCountOffice(bank.getCountOffice() - 1);
            }
        }
    }

    @Override
    public boolean approvalCredit(Bank bank, CreditAccount account, Employee employee) {
        if ((account != null) && (bank != null) && (employee != null)) {
            double sum = account.getMoney();
            if (bank.getMoney() >= sum) {
                if (employee.getIsGiveCredit()) {
                    double sumMonthPay = sum * (bank.getInterestRate() / 100 + 1) / account.getCountMonth();

                    if (account.getUser().getMonthIncome() >= sumMonthPay) {
                        account.setEmployee(employee);
                        account.setMonthPay(sumMonthPay);
                        account.setBank(bank);
                        account.setEmployee(employee);
                        account.setInterestRate(bank.getInterestRate());

                        LocalDate dateEnd = account.getDateStart();
                        dateEnd = dateEnd.plusMonths(account.getCountMonth());
                        account.setDateEnd(dateEnd);
                        return true;
                    } else {
                        System.out.println("Отказано в выдаче кредита! " +
                                account.getUser().getName() + " получает меньше ежемесячной выплаты за кредит\n");
                    }
                } else {
                    System.out.println("Отказано в выдаче кредита! " +
                            "Сотрудник " + employee.getName() + " не может оформлять кредиты\n");
                }
            } else {
                System.out.println("Отказано в выдаче кредита! " +
                        "В банке " + bank.getName() + " недостаточно денег для выдачи кредита\n");
            }
        }

        return false;
    }
}
