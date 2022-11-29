package tech.reliab.course.artemovaka.bank.service.impl;

import tech.reliab.course.artemovaka.bank.entity.Bank;
import tech.reliab.course.artemovaka.bank.entity.BankAtm;
import tech.reliab.course.artemovaka.bank.entity.BankOffice;
import tech.reliab.course.artemovaka.bank.service.AtmService;
import tech.reliab.course.artemovaka.bank.service.BankOfficeService;
import tech.reliab.course.artemovaka.bank.utils.Status;

public class AtmServiceImpl implements AtmService {

    @Override
    public BankAtm create(BankAtm bankAtm) {
        if (bankAtm != null) {
            if (bankAtm.getBankOffice() == null) {
                System.out.println("Ошибка! Нельзя создать банкомат без офиса!");
                return null;
            }
            BankOfficeService bankOfficeService = new BankOfficeServiceImpl();
            if (bankOfficeService.installAtm(bankAtm.getBankOffice(), bankAtm)) {
                return new BankAtm(bankAtm);
            }
        }
        return null;
    }

    @Override
    public void depositMoney(BankAtm bankAtm, double sum) {
        if ((bankAtm != null) && (bankAtm.getBankOffice() != null) && (bankAtm.getBank() != null)) {

            if (bankAtm.getStatus() != Status.NOT_WORK) {
                if (bankAtm.getIsPayInMoney()) {
                    BankOffice office = bankAtm.getBankOffice();
                    Bank bank = bankAtm.getBank();
                    double newSum = bankAtm.getMoney() + sum;

                    bankAtm.setMoney(newSum);
                    office.setMoney(office.getMoney() + newSum);
                    bank.setMoney(bank.getMoney() + newSum);
                } else {
                    System.out.println("В банкомат " + bankAtm.getName() + " нельзя вносить деньги\n");
                }
            } else {
                System.out.println("Банкомат " + bankAtm.getName() + " не работает\n");
            }
        }
    }

    @Override
    public void withdrawMoney(BankAtm bankAtm, double sum) {
        if ((bankAtm != null) && (bankAtm.getBankOffice() != null) && (bankAtm.getBank() != null)) {

            if (bankAtm.getStatus() == Status.WORK) {
                if (bankAtm.getIsGiveMoney()) {
                    if (bankAtm.getMoney() >= sum) {

                        BankOffice office = bankAtm.getBankOffice();
                        Bank bank = bankAtm.getBank();
                        double newSum = bankAtm.getMoney() - sum;

                        bankAtm.setMoney(newSum);
                        office.setMoney(office.getMoney() + newSum);
                        bank.setMoney(bank.getMoney() + newSum);
                    } else {
                        System.out.println("В банкомате" + bankAtm.getName() + " недостаточно денег для выдачи\n");
                    }
                } else {
                    System.out.println("Банкомат " + bankAtm.getName() + " не работает на выдачу денег\n");
                }
            }
        }
    }
}
