package tech.reliab.course.artemovaka.bank.service;

import tech.reliab.course.artemovaka.bank.entity.*;
import tech.reliab.course.artemovaka.bank.exceptions.NegativeAmountException;
import tech.reliab.course.artemovaka.bank.exceptions.ObjectCreatException;

import java.util.List;

public interface BankOfficeService {
	/**
	 * Создание офиса банка
	 **/
	BankOffice create(BankOffice bankOffice) throws NegativeAmountException, ObjectCreatException;

	/**
	 * Добавление офиса банка в массив
	 * Возвращает добавленный объект при успешном выполнении операции;
	 * Если bankOffice равен null или уже существует в массиве, возвращает null
	 * Логика добавления офиса передается bankService
	 **/
	BankOffice addBankOffice(BankOffice bankOffice) throws ObjectCreatException;

	/**
	 * Получение офиса банка по id
	 * Если объект не найден, возвращает null
	 **/
	public BankOffice getBankOfficeById(int bankOfficeId);

	/**
	 * Получение всех офисов
	 **/
	public List<BankOffice> getAllBankOffice();

	/**
	 * Получение всех офисов банка по его id
	 **/
	public List<BankOffice> getAllBankOfficeByIdBank(int bankId);

	/**
	 * Удаление офиса банка по id
	 * При удалении офиса удаляются все его банкоматы
	 * Логика удаления офиса передается bankService
	 **/
	Boolean deleteBankOffice(int bankOfficeId) throws NegativeAmountException;

	/**
	 * Вывод подробной информации о банковском офисе
	 */
	public String read(int bankOfficeId);

	/**
	 * Установка банкомата в офисе.
	 * При этом увеличивается кол-во банкоматов и денег в офисе
	 * При этом увеличивается количество банкоматов и денег в соответствующем банке.
	 * В операции может быть отказано, если в офисе запрещена установка банкоматов.
	 **/
	boolean addAtm(int bankOfficeId, BankAtm bankAtm) throws NegativeAmountException;

	/**
	 * Удаление банкомата из офиса.
	 * При этом уменьшается количество банкоматов и денег в офисе
	 * При этом уменьшается количество банкоматов и денег в соответствующем банке.
	 **/
	Boolean deleteAtm(int bankOfficeId, int idAtm) throws NegativeAmountException;

	/**
	 * Внести деньги в офис. Также деньги вносятся в соответствующий банк.
	 * В операции может быть отказано, если офис не работает на внос денег.
	 **/
	void depositMoney(int bankOfficeId, double sum) throws NegativeAmountException;

	/**
	 * Снять деньги из офиса. Также деньги снимаются из соответствующего банка.
	 * В операции может быть отказано, если офис не работает на выдачу денег или в нем недостаточно денег.
	 **/
	void withdrawMoney(int bankOfficeId, double sum) throws NegativeAmountException;

	/**
	 * Добавление работника в офис
	 * При этом увеличивается количество работников в соответствующем банке
	 **/
	Boolean addEmployee(int bankOfficeId, Employee employee);

	/**
	 * Удаление работника из офиса по ID.
	 * При этом уменьшается количество работников в соответствующем банке
	 **/
	Boolean deleteEmployee(int bankOfficeId, int id);

	/**
	 * Возвращает все офисы принадлежащие банку bankId.
	 */
	List<BankOffice> getAllBankOfficesByBankId(int bankId);
}
