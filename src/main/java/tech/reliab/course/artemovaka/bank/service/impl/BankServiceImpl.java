package tech.reliab.course.artemovaka.bank.service.impl;

import tech.reliab.course.artemovaka.bank.entity.*;
import tech.reliab.course.artemovaka.bank.exceptions.AtmIssuingMoneyException;
import tech.reliab.course.artemovaka.bank.exceptions.CreditException;
import tech.reliab.course.artemovaka.bank.exceptions.NegativeAmountException;
import tech.reliab.course.artemovaka.bank.exceptions.ShortageMoneyException;
import tech.reliab.course.artemovaka.bank.service.BankOfficeService;
import tech.reliab.course.artemovaka.bank.service.BankService;
import tech.reliab.course.artemovaka.bank.utils.BankComparator;

import java.time.LocalDate;
import java.util.*;


public class BankServiceImpl implements BankService {
    private static BankServiceImpl INSTANCE;

    private final Map<Integer, Bank> banks = new HashMap<>();

    private BankServiceImpl() {
    }

    public static BankServiceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BankServiceImpl();
        }

        return INSTANCE;
    }

    @Override
    public Bank create(Bank bank){
        if (bank != null) {
            if (bank.getId() < 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            Random random = new Random();
            int rating = random.nextInt(100);
            bank.setRating(rating);
            double money = random.nextDouble(1000000);
            bank.setMoney(money);
            calculateInterestRate(bank);
            return addBank(new Bank(bank));
        }

        return null;
    }

    @Override
    public Bank addBank(Bank bank) {
        if (bank != null) {
            if (!banks.containsKey(bank.getId())) {
                banks.put(bank.getId(), bank);
                return banks.get(bank.getId());
            } else {
                System.out.println("Нельзя добавить банк: банк с таким id уже существует");
            }
        } else {
            System.out.println("Нельзя добавить банк: банк не может быть null");
        }

        return null;
    }

    @Override
    public Bank getBankById(int bankId){
        Bank bank = banks.get(bankId);
        if (bank == null) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return bank;
    }

    @Override
    public Boolean deleteBankById(int bankId)throws NegativeAmountException{
        BankOfficeService bankOfficeService = BankOfficeServiceImpl.getInstance();
        if (banks.containsKey(bankId)) {

            // Удаляем все офисы
            List<BankOffice> bankOffices = bankOfficeService.getAllBankOfficeByIdBank(bankId);
            for (BankOffice bankOffice : bankOffices) {
                if (!deleteOffice(bankId, bankOffice.getId())) {
                    System.out.println("Не удалось удалить банк с id = " + bankId);
                    return false;
                }
            }
            return banks.remove(bankId) != null;
        }
        return false;
    }

    @Override
    public List<Bank> getAllBanks() {
        return new ArrayList<Bank>(banks.values());
    }

    @Override
    public String read(int bankId){
        BankOfficeServiceImpl bankOfficeService = BankOfficeServiceImpl.getInstance();
        UserServiceImpl userService = UserServiceImpl.getInstance();
        EmployeeServiceImpl employeeService = EmployeeServiceImpl.getInstance();

        Bank bank = getBankById(bankId);
        if (bank != null) {
            StringBuilder str = new StringBuilder(
                    "ID банка: " + bank.getId() + "\n" +
                            "Банк: " + bank.getName() + " \n" +
                            "Количество офисов: " + bank.getCountOffice() + "\n" +
                            "Количество банкоматов: " + bank.getCountAtm() + "\n" +
                            "Количество работников: " + bank.getCountEmployee() + "\n" +
                            "Количество клиентов: " + bank.getCountClient() + "\n" +
                            "Рейтинг: " + bank.getRating() + "\n" +
                            "Количество денег: " + String.format("%.4f", bank.getMoney()) + "\n" +
                            "Процентная ставка: " + String.format("%.2f", bank.getInterestRate()) + "\n"
            );

            str.append((bank.getCountOffice() > 0) ? "Информация о офисах:\n" : "");
            List<BankOffice> bankOffices = bankOfficeService.getAllBankOfficeByIdBank(bank.getId());
            for (BankOffice bankOffice : bankOffices) {
                str.append("-----------------------------------------------\n");
                str.append(bankOfficeService.read(bankOffice.getId()));
                str.append("-----------------------------------------------\n");
            }

            str.append((bank.getCountClient() > 0) ? "Информация о клиентах:\n" : "");
            List<User> users = userService.getAllUserByIdBank(bankId);
            for (User user : users) {
                str.append("-----------------------------------------------\n");
                str.append(user.toString());
                str.append("-----------------------------------------------\n");
            }

            return str.toString();
        }

        return "";
    }

    @Override
    public void calculateInterestRate(Bank bank) {
        if (bank != null) {
            Random random = new Random();
            int rating = bank.getRating();
            var offset = random.nextDouble() * 4;
            if (rating < 30) {
                bank.setInterestRate(offset + 16);             // [16; 20]
            } else if (rating < 60) {
                bank.setInterestRate(offset + 11);             // [11; 15]
            } else if (rating < 90) {
                bank.setInterestRate(offset + 6);               // [6; 10]
            } else {
                bank.setInterestRate(offset + 1);                // [1; 5]
            }
        }
    }

    @Override
    public void depositMoney(int bankId, double sum){
        Bank bank = getBankById(bankId);
        if (bank != null) {
            bank.setMoney(bank.getMoney() + sum);
        }
    }

    @Override
    public void withdrawMoney(int bankId, double sum){
        Bank bank = getBankById(bankId);
        if (bank != null) {
            if (bank.getMoney() >= sum) {
                bank.setMoney(bank.getMoney() - sum);
            } else {
                System.out.println("В банке " + bank.getName() + " недостаточно денег для выдачи\n");
            }
        }
    }

    @Override
    public Boolean addClient(int bankId, User user){
        Bank bank = getBankById(bankId);
        if ((bank != null) && (user != null)) {
            user.addBank(bank);
            bank.setCountClient(bank.getCountClient() + 1);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteClient(int bankId, int id){
        Bank bank = getBankById(bankId);
        if (bank != null) {
            int countClient = bank.getCountClient();
            if (countClient - 1 < 0) {
                System.out.println("Ошибка! Кол-во клиентов в банке не может быть отрицательным числом");
            } else {
                bank.setCountClient(countClient - 1);
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean addEmployee(int bankId, Employee employee){
        Bank bank = getBankById(bankId);
        if ((bank != null) && (employee != null)) {
            employee.setBank(bank);
            bank.setCountEmployee(bank.getCountEmployee() + 1);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteEmployee(int bankId, int id){
        Bank bank = getBankById(bankId);
        if (bank != null) {

            int countEmployee = bank.getCountEmployee();
            if (countEmployee - 1 < 0) {
                System.out.println("Ошибка! Кол-во работников в банке не может быть отрицательным числом");
            } else {
                bank.setCountClient(countEmployee - 1);
                return true;
            }
        }
        return false;
    }

    @Override
    public void addOffice(int bankId, BankOffice bankOffice){
        Bank bank = getBankById(bankId);
        if ((bank != null) && (bankOffice != null)) {
            bank.setCountOffice(bank.getCountOffice() + 1);
            bank.setCountAtm(bank.getCountAtm() + bankOffice.getCountAtm());
            depositMoney(bankId, bankOffice.getMoney());
        }
    }

    @Override
    public Boolean deleteOffice(int bankId, int bankOfficeId)throws NegativeAmountException{
        BankOfficeService bankOfficeService = BankOfficeServiceImpl.getInstance();
        Bank bank = getBankById(bankId);
        BankOffice bankOffice = bankOfficeService.getBankOfficeById(bankOfficeId);

        if ((bank != null) && (bankOffice != null)) {
            int countOffice = bank.getCountOffice();

            if (countOffice - 1 < 0) {
                System.out.println("Ошибка! Кол-во офисов в банке не может быть отрицательным числом");
            } else {
                if (bankOfficeService.deleteBankOffice(bankOfficeId)) {
                    bank.setCountOffice(bank.getCountOffice() - 1);
                    withdrawMoney(bankId, bankOffice.getMoney());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean approvalCredit(Bank bank, CreditAccount account, Employee employee) {
        if ((account != null) && (bank != null) && (employee != null)) {
            double sum = account.getMoney();
            if (bank.getMoney() >= sum) {
                if (employee.getIsGiveCredit()) {
                    double sumMonthPay = sum * (bank.getInterestRate() / 100 + 1) / account.getCountMonth();

                    if (account.getUser().getMonthIncome() >= sumMonthPay) {
                        account.setEmployee(employee);
                        account.setMonthPay(sumMonthPay);
                        account.setBank(bank);
                        account.setEmployee(employee);
                        account.setInterestRate(bank.getInterestRate());

                        LocalDate dateEnd = account.getDateStart();
                        dateEnd = dateEnd.plusMonths(account.getCountMonth());
                        account.setDateEnd(dateEnd);
                        return true;
                    } else {
                        System.out.println("Отказано в выдаче кредита! " +
                                account.getUser().getName() + " получает меньше ежемесячной выплаты за кредит\n");
                    }
                } else {
                    System.out.println("Отказано в выдаче кредита! " +
                            "Сотрудник " + employee.getName() + " не может оформлять кредиты\n");
                }
            } else {
                System.out.println("Отказано в выдаче кредита! " +
                        "В банке " + bank.getName() + " недостаточно денег для выдачи кредита\n");
            }
        }

        return false;
    }

    @Override
    public int issueLoan(int userId, double creditSum, int mountNumber)throws CreditException, ShortageMoneyException, NegativeAmountException, AtmIssuingMoneyException {
        var user = UserServiceImpl.getInstance().getUserById(userId);
        // получем список банков, отсортированных по правилу из лабораторной работы. Последний банк - самый лучший
        var listBanks = banks.values().stream().toList().stream().sorted(new BankComparator()).toList();
        for (int i = listBanks.size() - 1; i >= 0; i--) {
            var offices = BankOfficeServiceImpl.getInstance().getAllBankOfficesByBankId(listBanks.get(i).getId());
            for (int j = 0; j < offices.size(); j++) {
                if (!(offices.get(j).getIsWorking() && offices.get(j).getIsGiveMoney() &&
                        offices.get(j).getIsGiveCredit() && (offices.get(j).getMoney() >= creditSum))) {
                    continue;
                }
                var employees = EmployeeServiceImpl.getInstance().getEmployeeInOfficeWhichCanApplyLoan(offices.get(j).getId());
                if (employees == null) {
                    continue;
                }
                var atms = AtmServiceImpl.getInstance().getAllBankAtmByOfficeId(offices.get(j).getId());
                var e = -1;
                var l = -1;
                for (int k = 0; k < atms.size() && l == -1; k++) {
                    if (!(atms.get(k).getIsGiveMoney() && atms.get(k).getMoney() >= creditSum)) {
                        continue;
                    }
                    l = k;
                    e = employees.indexOf(atms.get(k).getEmployee());
                }
                if (l == -1 || e == -1) {
                    continue;
                }
                var paymentAccount = PaymentAccountServiceImpl.getInstance().paymentAccountRegistration(listBanks.get(i), user, 10000);
                var creditAccountService = CreditAccountServiceImpl.getInstance();
                var creditAccount = creditAccountService.createCreditAccount(listBanks.get(i), user,
                        paymentAccount, employees.get(e), creditSum, mountNumber);
                if (creditAccount == null) {
                    continue;
                }
                AtmServiceImpl.getInstance().withdrawMoney(atms.get(l).getId(), creditSum);
                creditAccountService.addCreditAccount(creditAccount);
                return 1;
            }
        }
        throw new CreditException(user);
    }

}
