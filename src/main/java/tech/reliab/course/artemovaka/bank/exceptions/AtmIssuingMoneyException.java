package tech.reliab.course.artemovaka.bank.exceptions;

import tech.reliab.course.artemovaka.bank.entity.BankAtm;

public class AtmIssuingMoneyException  extends Exception{
    public AtmIssuingMoneyException(BankAtm atm) {
        super("Банкомат " + atm.getId() + "не работает на выдачу денег!");
    }
}
