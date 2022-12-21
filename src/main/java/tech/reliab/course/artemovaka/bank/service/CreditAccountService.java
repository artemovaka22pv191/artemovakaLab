package tech.reliab.course.artemovaka.bank.service;

import tech.reliab.course.artemovaka.bank.entity.*;
import tech.reliab.course.artemovaka.bank.exceptions.NegativeAmountException;

import java.util.List;

public interface CreditAccountService {
    /** Создание кредитного аккаунта **/
    CreditAccount create(CreditAccount creditAccount) throws NegativeAmountException;

    /**
     * Добавление кредитного аккаунта
     * Логика добавления передается userService
     **/
    public CreditAccount addCreditAccount(CreditAccount creditAccount);

    /**
     * Получение кредитного аккаунта по id
     * Если объект не найден, возвращает null
     **/
    public CreditAccount getCreditAccountById(int creditAccountId);

    /**
     * Удаление кредитного аккаунта по id
     * Логика удаления передается userService
     **/
    public boolean deleteCreditAccountById(int creditAccountId);

    /**
     * Получение всех кредитных аккаунтов определенного пользователя
     **/
    public List<CreditAccount> getAllCreditAccountByIdUser(int userId);

    /**
     * Получение всех кредитных аккаунтов
     **/
    public List<CreditAccount> getAllCreditAccounts();

    /**
     * Вывод подробной информации о кредитном аккаунте
     */
    public String read(int creditAccountId);

    /**
    Списание ежемесячной оплаты для погашения кредита.
    В опреации может быть отказано, если на платежном аккаунте недостаточно средств или кредит уже погашен.
     **/
    void monthlyPayment(CreditAccount account);

    /**
     * Создаёт кредитный счёт
     */
    CreditAccount createCreditAccount(Bank bank, User user, PaymentAccount paymentAccount, Employee employee, Double sum, int monthNumber);
}
