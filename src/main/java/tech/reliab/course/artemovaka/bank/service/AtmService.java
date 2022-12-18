package tech.reliab.course.artemovaka.bank.service;

import tech.reliab.course.artemovaka.bank.entity.Bank;
import tech.reliab.course.artemovaka.bank.entity.BankAtm;
import tech.reliab.course.artemovaka.bank.entity.Employee;
import tech.reliab.course.artemovaka.bank.exceptions.AtmIssuingMoneyException;
import tech.reliab.course.artemovaka.bank.exceptions.NegativeAmountException;
import tech.reliab.course.artemovaka.bank.exceptions.ObjectCreatException;
import tech.reliab.course.artemovaka.bank.exceptions.ShortageMoneyException;

import java.util.List;

public interface AtmService {
	/**
	 * Создание банкомата
	 **/
	BankAtm create(BankAtm bankAtm) throws NegativeAmountException, ObjectCreatException;

	/**
	 * Добавление банкомата в массив
	 * Возвращает добавленный объект при успешном выполнении операции;
	 * Если bankAtm равен null или уже существует в массиве, возвращает null
	 * Логика добавления передается в bankOfficeService
	 **/
	BankAtm addBankAtm(BankAtm bankAtm) throws NegativeAmountException, ObjectCreatException;

	/**
	 * Установка - работник empl обслуживает банкомат bankAtmId
	 **/
	void setEmplByBankAtm(int bankAtmId, Employee empl);

	/**
	 * Получение банкомата по id
	 * Если объект не найден, возвращает null
	 **/
	public BankAtm getBankAtmById(int bankAtmId);

	/**
	 * Получение всех банкоматов
	 **/
	public List<BankAtm> getAllBankAtm();

	/**
	 * Получение всех банкоматов определенного офиса
	 **/
	public List<BankAtm> getAllBankAtmByIdBankOffice(int bankOfficeId);

	/**
	 * Удаление банкомата по id из массива
	 * Логика удаления передается bankOfficeService
	 **/
	Boolean deleteBankAtm(int bankAtmId) throws NegativeAmountException;

	/**
	 * Вывод подробной информации о банкомате
	 */
	public String read(int bankAtmId);

	/**
	 * Внести деньги в банкомат. Также деньги вносятся в соответствующий офис и банк.
	 * В операции может быть отказано, если банкомат не работает в текущий момент или не работает на внос денег.
	 **/
	void depositMoney(int bankAtmId, double sum) throws NegativeAmountException;

	/**
	 * Снять деньги из банкомата. Также деньги снимаются из соответствующего офиса и банка.
	 * В операции может быть отказано, если банкомат не работает в текущий момент, не работает на выдачу денег
	 * или в нем недостаточно денег.
	 **/
	void withdrawMoney(int bankAtmId, double sum) throws ShortageMoneyException, NegativeAmountException, AtmIssuingMoneyException;

	void updateBank(int id, Bank bank);

	/**
	 * Возвращает все банкоматы, которые обслуживает данный сотрудник emplId
	 */
	List<BankAtm> getAllBankAtmByEmplId(int emplId);

	/**
	 * Возвращает все банкоматы офиса offId
	 */
	List<BankAtm> getAllBankAtmByOfficeId(int offId);
}
