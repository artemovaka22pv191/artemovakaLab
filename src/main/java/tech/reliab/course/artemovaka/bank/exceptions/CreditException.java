package tech.reliab.course.artemovaka.bank.exceptions;

import tech.reliab.course.artemovaka.bank.entity.User;

public class CreditException extends Exception {
    public CreditException(User user) {
        super("Кредит клиенту " + user.getId() + " не был выдан !");
    }
}