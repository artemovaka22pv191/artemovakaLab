package tech.reliab.course.artemovaka.bank.service.impl;

import tech.reliab.course.artemovaka.bank.entity.BankAtm;
import tech.reliab.course.artemovaka.bank.entity.BankOffice;
import tech.reliab.course.artemovaka.bank.entity.Employee;
import tech.reliab.course.artemovaka.bank.service.BankOfficeService;
import tech.reliab.course.artemovaka.bank.service.BankService;

public class BankOfficeServiceImpl implements BankOfficeService {

    @Override
    public BankOffice create(BankOffice bankOffice) {
        if (bankOffice != null) {
            if (bankOffice.getBank() != null) {
                if (bankOffice.getBank().getMoney() < bankOffice.getMoney()) {
                    System.out.println("Ошибка! Кол-во денег в офисе не может превышать кол-во денег в банке!");
                    return null;
                }
            } else {
                System.out.println("Ошибка! Нельзя создать офис без банка!");
                return null;
            }
            BankService bankService = new BankServiceImpl();
            bankService.addOffice(bankOffice.getBank(), bankOffice);
            return new BankOffice(bankOffice);
        }

        return null;
    }

    @Override
    public boolean installAtm(BankOffice bankOffice, BankAtm bankAtm) {
        if ((bankOffice != null) && (bankAtm != null) && (bankOffice.getBank() != null)) {
            if (bankOffice.getIsInstallAtm()) {
                bankOffice.setCountAtm(bankOffice.getCountAtm() + 1);
                bankOffice.getBank().setCountAtm(bankOffice.getBank().getCountAtm() + 1);
                bankAtm.setBankOffice(bankOffice);
                bankAtm.setAddress(bankOffice.getAddress());
                return true;
            } else {
                System.out.println("В офисе " + bankOffice.getName() + " нельзя устанавливать банкоматы");
            }
        }
        return false;
    }

    @Override
    public void uninstallAtm(BankOffice bankOffice) {
        if ((bankOffice != null) && (bankOffice.getBank() != null)) {
            bankOffice.setCountAtm(bankOffice.getCountAtm() - 1);
            bankOffice.getBank().setCountAtm(bankOffice.getBank().getCountAtm() - 1);
        }
    }

    @Override
    public void depositMoney(BankOffice bankOffice, double sum) {
        if ((bankOffice != null) && (bankOffice.getBank() != null)) {
            if (bankOffice.getIsPayInMoney()) {
                bankOffice.setMoney(bankOffice.getMoney() + sum);
                bankOffice.getBank().setMoney(bankOffice.getBank().getMoney() + sum);
            } else {
                System.out.println("В офис " + bankOffice.getName() + " нельзя вносить деньги");
            }
        }
    }

    @Override
    public void withdrawMoney(BankOffice bankOffice, double sum) {
        if ((bankOffice != null) && (bankOffice.getBank() != null)) {
            if (bankOffice.getIsGiveMoney()) {
                if (bankOffice.getMoney() >= sum) {
                    bankOffice.setMoney(bankOffice.getMoney() - sum);
                    bankOffice.getBank().setMoney(bankOffice.getBank().getMoney() - sum);
                } else {
                    System.out.println("В банкомате " + bankOffice.getBank().getName() + " недостаточно денег для выдачи\n");
                }
            } else {
                System.out.println("Офис " + bankOffice.getName() + " не работает на выдачу денег\n");
            }
        }
    }

    @Override
    public void addEmployee(BankOffice bankOffice, Employee employee) {
        if ((employee != null) && (bankOffice != null)) {
            employee.setOffice(bankOffice);
            employee.setBank(bankOffice.getBank());
            employee.getBank().setCountEmployee(employee.getBank().getCountEmployee() + 1);
        }
    }

    @Override
    public void removeEmployee(BankOffice bankOffice) {
        if ((bankOffice != null) && (bankOffice.getBank() != null)) {
            bankOffice.getBank().setCountEmployee(bankOffice.getBank().getCountEmployee() - 1);
        }
    }
}
