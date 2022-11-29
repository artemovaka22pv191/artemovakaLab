package tech.reliab.course.artemovaka.bank.service;

import tech.reliab.course.artemovaka.bank.entity.PaymentAccount;

public interface PaymentAccountService {

    /**
     * Создание платежного аккаунта
     */
    PaymentAccount create(PaymentAccount paymentAccount);

    /**
     * Внести деньги на платежный счет.
     */
    void depositMoney(PaymentAccount account, double sum);

    /**
    Снять деньги с платежного счета.
    В операции может быть отказано, если на счету недостаточно денег.
    */

    void withdrawMoney(PaymentAccount account, double sum);
}
