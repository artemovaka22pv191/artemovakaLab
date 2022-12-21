package tech.reliab.course.artemovaka.bank.exceptions;

import tech.reliab.course.artemovaka.bank.entity.BankAtm;

public class TransferAccountException extends Exception{
    public TransferAccountException() {
        super("Ошибка! Введенный счет не был переведен!");
    }
}
