package tech.reliab.course.artemovaka.bank.exceptions;

import tech.reliab.course.artemovaka.bank.entity.BankAtm;

public class ShortageMoneyException extends Exception{
    public ShortageMoneyException(BankAtm bankAtm) {
        super("Ошибка! Введена отрицательная сумма");
    }
}
