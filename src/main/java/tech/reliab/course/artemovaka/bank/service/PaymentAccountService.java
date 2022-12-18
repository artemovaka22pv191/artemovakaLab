package tech.reliab.course.artemovaka.bank.service;

import tech.reliab.course.artemovaka.bank.entity.Bank;
import tech.reliab.course.artemovaka.bank.entity.PaymentAccount;
import tech.reliab.course.artemovaka.bank.entity.User;
import tech.reliab.course.artemovaka.bank.exceptions.NegativeAmountException;

import java.util.List;

public interface PaymentAccountService {

	/**
	 * Создание платежного аккаунта
	 **/
	PaymentAccount create(PaymentAccount paymentAccount) throws NegativeAmountException;

	/**
	 * Добавление платежного аккаунта
     * Логика добавления передается userService
	 **/
	PaymentAccount addPaymentAccount(PaymentAccount paymentAccount);

	/**
	 * Получение платежного аккаунта по id
	 **/
	PaymentAccount getPaymentAccountById(int paymentAccountId);

	/**
	 * Удаление платежного аккаунта
     * Логика удаления передается userService
	 **/
	boolean deletePaymentAccountById(int paymentAccountId);

	/**
	 * Получение всех платежных аккаунтов
	 **/
	List<PaymentAccount> getAllPaymentAccounts();

	/**
	Внести деньги на платежный счет.
	**/
	void depositMoney(PaymentAccount account, double sum) throws NegativeAmountException;

    /**
    Снять деньги с платежного счета.
    В операции может быть отказано, если на счету недостаточно денег.
    **/

	void withdrawMoney(PaymentAccount account, double sum) throws NegativeAmountException;

	List<PaymentAccount> getAllPaymentAccountByIdUser(int userId);

	String read(int userId);

	/**
	 * Создаёт счёт, при условии, что у пользователя нет счёта в указанном банке
	 * @return возвращает найденный или созданный счёт
	 */
	PaymentAccount paymentAccountRegistration(Bank bank, User user, double money) throws NegativeAmountException;
}
