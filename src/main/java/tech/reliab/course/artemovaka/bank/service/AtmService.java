package tech.reliab.course.artemovaka.bank.service;

import tech.reliab.course.artemovaka.bank.entity.BankAtm;

public interface AtmService {
    /**
     * Создание банкомата
     */
    BankAtm create(BankAtm bankAtm);

    /**
     * Внести деньги в банкомат. Также деньги вносятся в соответствующий офис и банк.
     * В операции может быть отказано, если банкомат не работает на текущий момент или не работает на внос денег.
     */
    void depositMoney(BankAtm bankAtm, double sum);

    /**
     * Снять деньги из банкомата. Также деньги снимаются из соответствующего офиса и банка.
     * В операции может быть отказано, если банкомат не работает в текущий момент, не работает на выдачу денег
     * или в нем недостаточно денег.
     */
    void withdrawMoney(BankAtm bankAtm, double sum);
}
