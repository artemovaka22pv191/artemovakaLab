package tech.reliab.course.artemovaka.bank.service.impl;

import tech.reliab.course.artemovaka.bank.entity.Bank;
import tech.reliab.course.artemovaka.bank.entity.BankAtm;
import tech.reliab.course.artemovaka.bank.entity.BankOffice;
import tech.reliab.course.artemovaka.bank.entity.Employee;
import tech.reliab.course.artemovaka.bank.exceptions.NegativeAmountException;
import tech.reliab.course.artemovaka.bank.exceptions.ObjectCreatException;
import tech.reliab.course.artemovaka.bank.service.AtmService;
import tech.reliab.course.artemovaka.bank.service.BankOfficeService;
import tech.reliab.course.artemovaka.bank.service.BankService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankOfficeServiceImpl implements BankOfficeService {
	private static BankOfficeServiceImpl INSTANCE;
	private final Map<Integer, BankOffice> bankOffices = new HashMap<>();

	private BankOfficeServiceImpl() {
	}

	public static BankOfficeServiceImpl getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new BankOfficeServiceImpl();
		}

		return INSTANCE;
	}

	private final BankService bankService = BankServiceImpl.getInstance();

	@Override
	public BankOffice create(BankOffice bankOffice)throws NegativeAmountException, ObjectCreatException{
		if (bankOffice != null) {
			if (bankOffice.getId() < 0) {
				throw new ArrayIndexOutOfBoundsException();
			}
			if (bankOffice.getMoney() < 0) {
				throw new NegativeAmountException();
			}
			if (bankOffice.getBank() != null) {
				if (bankOffice.getBank().getMoney() < bankOffice.getMoney()) {
					System.out.println("Ошибка! Кол-во денег в офисе не может превышать кол-во денег в банке!");
					return null;
				}
			} else {
				throw new ObjectCreatException("офис", "банк");
			}
			if (bankOffice.getRentPrice() < 0) {
				throw new NegativeAmountException();
			}
			return addBankOffice(new BankOffice(bankOffice));
		}
		return null;
	}

	@Override
	public BankOffice addBankOffice(BankOffice bankOffice)throws ObjectCreatException{
		if (bankOffice != null) {
			if (!bankOffices.containsKey(bankOffice.getId())) {
				Bank bank = bankOffice.getBank();
				if (bank != null) {
					bankOffices.put(bankOffice.getId(), bankOffice);
					bankService.addOffice(bank.getId(), bankOffice);
					return bankOffices.get(bankOffice.getId());
				} else {
					throw new ObjectCreatException("офис", "банк");
				}
			} else {
				System.out.println("Нельзя добавить офис: офис с таким id уже существует");
			}
		} else {
			System.out.println("Нельзя добавить офис: офис не может быть null");
		}

		return null;
	}

	@Override
	public BankOffice getBankOfficeById(int bankOfficeId){
		BankOffice bankOffice = bankOffices.get(bankOfficeId);
		if (bankOffice == null) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return bankOffice;
	}

	@Override
	public Boolean deleteBankOffice(int bankOfficeId)throws NegativeAmountException{
		AtmService atmService = AtmServiceImpl.getInstance();
		BankOffice bankOffice = bankOffices.get(bankOfficeId);
		if (bankOffice != null) {
			// Удаляем все банкоматы офиса
			List<BankAtm> bankAtms = atmService.getAllBankAtmByIdBankOffice(bankOfficeId);
			for (BankAtm bankAtm: bankAtms) {
				if (!deleteAtm(bankOfficeId, bankAtm.getId()))  {
					System.out.println("Не удалось удалить офис с id = " + bankOfficeId);
					return false;
				}
			}
			if (bankService.deleteOffice(bankOffice.getBank().getId(), bankOfficeId)) {
				return bankOffices.remove(bankOfficeId) != null;
			}
		}
		return false;
	}

	@Override
	public List<BankOffice> getAllBankOffice() {
		return new ArrayList<BankOffice>(bankOffices.values());
	}

	@Override
	public List<BankOffice> getAllBankOfficeByIdBank(int id) {
		List<BankOffice> officesByBank = bankOffices.values().stream().filter(
				office -> office.getBank().getId() == id).toList();

		return officesByBank;
	}

	@Override
	public String read(int bankOfficeId){
		AtmService atmService = AtmServiceImpl.getInstance();
		EmployeeServiceImpl employeeService = EmployeeServiceImpl.getInstance();
		BankOffice bankOffice = getBankOfficeById(bankOfficeId);
		if (bankOffice != null) {
			StringBuilder str = new StringBuilder("ID офиса: " + bankOffice.getId() + "\n" +
					"Банковский офис: " + bankOffice.getName() + "\n" +
					"Банк: " + (bankOffice.getBank() != null ? bankOffice.getBank().getName() : "") + " \n" +
					"Адрес: " + bankOffice.getAddress() + "\n" +
					"Работает: " + (bankOffice.getIsWorking()? "да" : "нет") + "\n" +
					"Можно устанавливать банкоматы: " + (bankOffice.getIsInstallAtm() ? "да" : "нет") + "\n" +
					"Кол-во банкоматов: " + bankOffice.getCountAtm() + "\n" +
					"Кол-во работников: " + bankOffice.getCountEmployee() + "\n" +
					"Выдает кредиты: " + (bankOffice.getIsGiveCredit() ? "да" : "нет") + "\n" +
					"Выдает деньги: " + (bankOffice.getIsGiveMoney() ? "да" : "нет") + "\n" +
					"Можно вносить деньги: " + (bankOffice.getIsPayInMoney() ? "да" : "нет") + "\n" +
					"Кол-во денег: " + String.format("%.4f", bankOffice.getMoney()) + "\n" +
					"Стоимость аренды: " + String.format("%.4f", bankOffice.getRentPrice()) + "\n");

			str.append((bankOffice.getCountAtm() > 0) ? "Информация о банкоматах :\n" : "");
			List<BankAtm> bankAtms = atmService.getAllBankAtmByIdBankOffice(bankOffice.getId());
			for (BankAtm bankAtm:  bankAtms) {
				str.append("..............................................................\n");
				str.append(atmService.read(bankAtm.getId()));
				str.append("..............................................................\n");
			}

			str.append((bankOffice.getCountEmployee() > 0) ? "Информация о работниках:\n" : "");
			List<Employee> employees = employeeService.getAllEmployeeByIdBankOffice(bankOfficeId);
			for (Employee employee: employees) {
				str.append("..............................................................\n");
				str.append(employeeService.read(employee.getId()));
				str.append("..............................................................\n");
			}
			return str.toString();
		}

		return "";
	}

	@Override
	public boolean addAtm(int bankOfficeId, BankAtm bankAtm)throws NegativeAmountException{
		BankOffice bankOffice = getBankOfficeById(bankOfficeId);

		if ((bankOffice != null) && (bankAtm != null) && (bankOffice.getBank() != null)) {
			if (bankOffice.getIsInstallAtm()) {
				bankOffice.setCountAtm(bankOffice.getCountAtm() + 1);
				bankOffice.getBank().setCountAtm(bankOffice.getBank().getCountAtm() + 1);
				bankAtm.setBankOffice(bankOffice);
				bankAtm.setBank(bankOffice.getBank());
				bankAtm.setAddress(bankOffice.getAddress());
				return true;
			} else {
				System.out.println("В офисе " + bankOffice.getName() + " нельзя устанавливать банкоматы");
			}
		}

		return false;
	}

	@Override
	public Boolean deleteAtm(int bankOfficeId, int idAtm)throws NegativeAmountException{
		AtmService atmService = AtmServiceImpl.getInstance();
		BankOffice bankOffice = getBankOfficeById(bankOfficeId);
		BankAtm bankAtm = atmService.getBankAtmById(idAtm);

		if ((bankOffice != null) && (bankOffice.getBank() != null) && (bankAtm != null)) {
			double sum = bankAtm.getMoney();

			if (atmService.deleteBankAtm(idAtm) != null) {
				bankOffice.setCountAtm(bankOffice.getCountAtm() - 1);
				bankOffice.getBank().setCountAtm(bankOffice.getBank().getCountAtm() - 1);
				return true;
			}
		}
		return false;
	}

	@Override
	public void depositMoney(int bankOfficeId, double sum)throws NegativeAmountException{
		if(sum < 0){
			throw new NegativeAmountException();
		}
		BankOffice bankOffice = getBankOfficeById(bankOfficeId);
		if ((bankOffice != null) && (bankOffice.getBank() != null)) {
			if (bankOffice.getIsPayInMoney()) {
				bankOffice.setMoney(bankOffice.getMoney() + sum);
				bankService.depositMoney(bankOffice.getBank().getId(), sum);
			} else {
				System.out.println("В офис " + bankOffice.getName() + " нельзя вносить деньги");
			}
		}
	}

	@Override
	public void withdrawMoney(int bankOfficeId, double sum)throws NegativeAmountException{
		if(sum < 0){
			throw new NegativeAmountException();
		}
		BankOffice bankOffice = getBankOfficeById(bankOfficeId);
		if ((bankOffice != null) && (bankOffice.getBank() != null)) {
			if (bankOffice.getIsGiveMoney()) {
				if (bankOffice.getMoney() >= sum) {
					bankOffice.setMoney(bankOffice.getMoney() - sum);
					bankService.withdrawMoney(bankOffice.getBank().getId(), sum);
				} else {
					System.out.println("В банкомате " + bankOffice.getBank().getName() + " недостаточно денег для выдачи\n");
				}

			} else {
				System.out.println("Офис " + bankOffice.getName() + " не работает на выдачу денег\n");
			}
		}
	}

	@Override
	public Boolean addEmployee(int bankOfficeId, Employee employee){
		BankOffice bankOffice = getBankOfficeById(bankOfficeId);
		if ((employee != null) && (bankOffice != null)) {
			employee.setOffice(bankOffice);
			employee.setBank(bankOffice.getBank());
			if (bankService.addEmployee(employee.getBank().getId(), employee)) {
				bankOffice.setCountEmployee(bankOffice.getCountEmployee() + 1);
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean deleteEmployee(int bankOfficeId, int id){
		BankOffice bankOffice = getBankOfficeById(bankOfficeId);
		if ((bankOffice != null) && (bankOffice.getBank() != null)) {
			return bankService.deleteEmployee(bankOffice.getBank().getId(), id);
		}
		return false;
	}

	@Override
	public List<BankOffice> getAllBankOfficesByBankId(int bankId){
		return bankOffices.values().stream().toList().stream()
				.filter(bankOffice -> bankOffice.getBank().getId() == bankId)
				.toList();
	}
}
