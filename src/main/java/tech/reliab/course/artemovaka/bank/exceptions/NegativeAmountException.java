package tech.reliab.course.artemovaka.bank.exceptions;

import tech.reliab.course.artemovaka.bank.entity.BankAtm;

public class NegativeAmountException extends Exception{
    public NegativeAmountException() {
        super("Сумма не может быть отрицательным числом!");
    }
}