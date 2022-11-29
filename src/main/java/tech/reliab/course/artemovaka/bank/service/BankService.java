package tech.reliab.course.artemovaka.bank.service;

import tech.reliab.course.artemovaka.bank.entity.*;

public interface BankService {
    /**
     * Создание банка
     */
    Bank create(Bank bank);

    /**
     * Вычисление процентной ставки банка (зависит от общей суммы денег в банке: чем больше денег - тем меньше ставка).
     */
    void calculateInterestRate(Bank bank);

    /**
     * Внести деньги sum в банк.
     */
    void depositMoney(Bank bank, double sum);

    /**
     * Снять деньги sum из банка.
     * В операции может быть отказано, если в банке недостаточно денег.
     */
    void withdrawMoney(Bank bank, double sum);

    /**
     * Добавление клиента банка
     */
    void addClient(Bank bank, User user);

    /**
     * Удаление клиента банка
     */
    void removeClient(Bank bank);

    /**
     * Добавить работника
     */
    void addEmployee(Bank bank, Employee employee);

    /**
     * Удалить работника
     */
    void removeEmployee(Bank bank);

    /**
     * Добавление офиса
     */
    void addOffice(Bank bank, BankOffice bankOffice);

    /**
     * Удаление офиса
     */
    void removeOffice(Bank bank, BankOffice bankOffice);

    /**
     * Одобрение заявки на кредит.
     * В операции может быть отказано, если в банке недостаточно денег, сотрудник employee не выдает кредиты или
     * зп клиента меньше, чем ежемесячная выплата по кредиту
     * Если операция проходит успешно, в account автоматически заполняется поле dateEnd.
     */
    boolean approvalCredit(Bank bank, CreditAccount account, Employee employee);
}
