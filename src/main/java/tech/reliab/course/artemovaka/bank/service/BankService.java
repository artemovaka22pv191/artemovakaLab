package tech.reliab.course.artemovaka.bank.service;

import tech.reliab.course.artemovaka.bank.entity.*;
import tech.reliab.course.artemovaka.bank.exceptions.AtmIssuingMoneyException;
import tech.reliab.course.artemovaka.bank.exceptions.CreditException;
import tech.reliab.course.artemovaka.bank.exceptions.NegativeAmountException;
import tech.reliab.course.artemovaka.bank.exceptions.ShortageMoneyException;

import java.util.List;

public interface BankService {
	/**
	 * Создание банка
	 **/
	Bank create(Bank bank);

	/**
	 * Добавление банка
	 * Возвращает добавленный объект при успешном выполнении операции;
	 * Если bank равен null или уже существует в массиве, возвращает null
	 **/
	public Bank addBank(Bank bank);

	/**
	 * Получение банка по id
	 * Если объект не найден, возвращает null
	 **/
	public Bank getBankById(int bankId);

	/**
	 * Удаление банка по id
	 * Возвращает true при успешном выполнении операции, иначе false
	 * При удалении банка удаляются все офисы банка
	 **/
	public Boolean deleteBankById(int bankId) throws NegativeAmountException;

	/**
	 * Получение всех банков
	 **/
	public List<Bank> getAllBanks();

	/**
	 * Вывод подробной информации о банке
	 */
	public String read(int bankId);

	/**
	 * Вычисление процентной ставки банка (зависит от общей суммы денег в банке: чем больше денег - тем меньше ставка).
	 **/
	void calculateInterestRate(Bank bank);

	/**
	 * Внести деньги sum в банк.
	 **/
	void depositMoney(int bankId, double sum);

	/**
	 * Снять деньги sum из банка.
	 * В операции может быть отказано, если в банке недостаточно денег.
	 **/
	void withdrawMoney(int bankId, double sum);

	/**
	 * Добавление клиента user в банк bank
	 **/
	Boolean addClient(int bankId, User user);

	/**
	 * Удаление клиента банка по ID
	 **/
	Boolean deleteClient(int bankId, int id);

	/**
	 * Добавить работника
	 **/
	Boolean addEmployee(int bankId, Employee employee);

	/**
	 * Удалить работника
	 **/
	Boolean deleteEmployee(int bankId, int id);

	/**
	 * Добавление офиса в банк
	 * При этом увеличивается кол-во офисов и банкоматов в банке, а также сумма денег
	 **/
	void addOffice(int bankId, BankOffice bankOffice);

	/**
	 * Удаление офиса
	 * Не может удалить офис, если кол-во офисов = 0
	 * При удалении уменьшает кол-во офисов и банкоматов у банка, а также снимает все деньги, лежащие в офисе
	 **/
	Boolean deleteOffice(int bankId, int bankOfficeId) throws NegativeAmountException;

	/**
	 * Одобрение заявки на кредит.
	 * В операции может быть отказано, если в банке недостаточно денег, сотрудник employee не выдает кредиты или
	 * зп клиента меньше, чем ежемесячная выплата по кредиту
	 * Если операция проходит успешно, в account автоматически заполняется поле dateEnd.
	 **/
	boolean approvalCredit(Bank bank, CreditAccount account, Employee employee);

	/**
	 * Выдаёт кредит пользователю
	 * userId id пользователя, которому будет выдан кредит
	 * creditSum сумма, на которую будет выдан кредит
	 */
	void issueLoan(int userId, double creditSum, int mountNumber) throws CreditException, ShortageMoneyException, NegativeAmountException, AtmIssuingMoneyException;
}
