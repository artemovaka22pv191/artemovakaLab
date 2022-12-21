package tech.reliab.course.artemovaka.bank;

import tech.reliab.course.artemovaka.bank.entity.*;
import tech.reliab.course.artemovaka.bank.exceptions.*;
import tech.reliab.course.artemovaka.bank.service.impl.*;
import tech.reliab.course.artemovaka.bank.utils.Status;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws CreditException, ShortageMoneyException, NegativeAmountException, ObjectCreatException {
        BankServiceImpl bankService = BankServiceImpl.getInstance();
        BankOfficeServiceImpl bankOfficeService = BankOfficeServiceImpl.getInstance();
        AtmServiceImpl atmService = AtmServiceImpl.getInstance();
        UserServiceImpl userService = UserServiceImpl.getInstance();
        EmployeeServiceImpl employeeService = EmployeeServiceImpl.getInstance();
        PaymentAccountServiceImpl paymentAccountService = PaymentAccountServiceImpl.getInstance();
        CreditAccountServiceImpl creditAccountService = CreditAccountServiceImpl.getInstance();
        try (Scanner in = new Scanner(System.in)) {
            bankService.create(new Bank(1, "Банк1"));
            bankService.create(new Bank(2, "Банк2"));
            bankService.create(new Bank(3, "Банк3"));
            bankService.create(new Bank(4, "Банк4"));
            bankService.create(new Bank(5, "Банк5"));

            // Создание по 3 офиса в каждом банке
            List<Bank> banks = bankService.getAllBanks();
            Random random = new Random();
            int id = 1;
            for (Bank bank : banks) {
                bankOfficeService.create(new BankOffice(
                        id, "Офис " + id, "Адресс " + id, bank, true,
                        true, true, true, true, bank.getMoney() / 3,
                        random.nextInt(100) + 50));
                id += 1;

                bankOfficeService.create(new BankOffice(
                        id, "Офис " + id, "Адресс " + id, bank, true,
                        true, false, false, true, bank.getMoney() / 3,
                        random.nextInt(100) + 30));
                id += 1;

                bankOfficeService.create(new BankOffice(
                        id, "Офис " + id, "Адресс " + id, bank, true,
                        true, true, true, true, bank.getMoney() / 3,
                        random.nextInt(100) + 200));
                id += 1;
            }

            // Создание по 2 банкомата в каждом офисе
            // 1 Работает, 1 Не работает
            List<BankOffice> bankOffices = bankOfficeService.getAllBankOffice();
            id = 1;
            for (BankOffice bankOffice : bankOffices) {
                atmService.create(new BankAtm(id, "Банкомат " + id + " офиса " + bankOffice.getName(), Status.WORK, true,
                        true, bankOffice.getMoney() / 2, random.nextInt(100) + 100, bankOffice, bankOffice.getBank(), null));

                id += 1;

                atmService.create(new BankAtm(
                        id, "Банкомат " + id + " офиса " + bankOffice.getName(), Status.NOT_WORK, false,
                        true, bankOffice.getMoney() / 2, random.nextInt(100) + 100, bankOffice, bankOffice.getBank(), null));

                id += 1;
            }

            // Создание по 5 работников в каждом офисе
            // 3 работника выдают кредит, 2 нет
            id = 1;
            for (BankOffice bankOffice : bankOffices) {
                employeeService.create(new Employee(
                        id, "Работник " + id + " офиса " + bankOffice.getName(),
                        LocalDate.of(2001, random.nextInt(11) + 1, random.nextInt(27) + 1),
                        "должность", true, bankOffice, true, random.nextInt(5000) + 1000));

                atmService.setEmplByBankAtm(atmService.getAllBankAtmByOfficeId(bankOffice.getId()).get(0).getId(), employeeService.getEmployeeById(id));
                id += 1;
                employeeService.create(new Employee(
                        id, "Работник " + id + " офиса " + bankOffice.getName(),
                        LocalDate.of(2004, random.nextInt(11) + 1, random.nextInt(27) + 1),
                        "должность", true, bankOffice, true, random.nextInt(3000) + 1000));
                atmService.setEmplByBankAtm(atmService.getAllBankAtmByOfficeId(bankOffice.getId()).get(1).getId(), employeeService.getEmployeeById(id));
                id += 1;
                employeeService.create(new Employee(
                        id, "Работник " + id + " офиса " + bankOffice.getName(),
                        LocalDate.of(2004, random.nextInt(11) + 1, random.nextInt(27) + 1),
                        "должность", false, bankOffice, true, random.nextInt(2000) + 3000));
                id += 1;

                employeeService.create(new Employee(
                        id, "Работник " + id + " офиса " + bankOffice.getName(),
                        LocalDate.of(2004, random.nextInt(11) + 1, random.nextInt(27) + 1),
                        "должность", false, bankOffice, false, random.nextInt(5000) + 1000));
                id += 1;

                employeeService.create(new Employee(
                        id, "Работник " + id + " офиса " + bankOffice.getName(),
                        LocalDate.of(2004, random.nextInt(11) + 1, random.nextInt(27) + 1),
                        "должность", true, bankOffice, false, random.nextInt(5000) + 2000));
                id += 1;
            }

            // Создание по 2 пользователя в банке
            id = 1;
            for (Bank bank : banks) {
                var user = new User(
                        id, "Пользователь " + id,
                        LocalDate.of(2004, random.nextInt(11) + 1,
                                random.nextInt(27) + 1), random.nextInt(3000) + 2000,
                        "адрес работы");
                userService.create(user);
                bankService.addClient(bank.getId(), user);
                id += 1;
                user = new User(
                        id, "Пользователь " + id,
                        LocalDate.of(2004, random.nextInt(11) + 1,
                                random.nextInt(27) + 1), random.nextInt(3000) + 2000,
                        "адрес работы");
                userService.create(user);
                bankService.addClient(bank.getId(), user);
                id += 1;
            }
            StringBuilder infoUserOption = new StringBuilder("Клиенты:\n");
            List<User> allUsers = userService.getAllUsers();
            for (User user : allUsers) {
                infoUserOption.append(user.getId());
                infoUserOption.append(" -- ");
                infoUserOption.append(user.getName());
                infoUserOption.append("\n");
            }
            infoUserOption.append("----------------------------------------\n");
            System.out.println(infoUserOption);

            var inputValue = 1;
            System.out.println("Введите 0, если хотите просмотреть банки, иначе введите 1");
            inputValue = in.nextInt();
            if (inputValue == 0) {
                StringBuilder infoBankOption = new StringBuilder("Банки:\n");
                List<Bank> allBanks = bankService.getAllBanks();
                for (Bank bank : allBanks) {
                    infoBankOption.append(bank);
                    infoBankOption.append("\n----------------------------------------\n");
                }
                System.out.println(infoBankOption);
            }
            while (inputValue != -1) {
                System.out.println("Введите id клиента, который берет кредит: ");
                var userkId = in.nextInt();
                System.out.println("Введите сумму кредита : ");
                var sum = in.nextInt();
                System.out.println("Кол-во месяцев для взятия кредита : ");
                var countMonth = in.nextInt();
                try {
                    bankService.issueLoan(userkId, sum, countMonth);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                finally {
                    System.out.println("Введите -1 для выхода : ");
                    inputValue = in.nextInt();
                    in.nextLine();
                }
            }

            StringBuilder accountOption = new StringBuilder("----------------------------------------\n");

            // Опция вывода платежных счетов
            List<PaymentAccount> paymentAccountsList = paymentAccountService.getAllPaymentAccounts();
            accountOption.append("Платежные счета\n");
            accountOption.append("----------------------------------------\n");
            for (PaymentAccount account : paymentAccountsList) {
                accountOption.append(account);
                accountOption.append("\n----------------------------------------\n");
            }
            // Опция вывода кредитных счетов
            List<CreditAccount> creditAccountsList = creditAccountService.getAllCreditAccounts();
            accountOption.append("Кредитные счета\n");
            accountOption.append("----------------------------------------\n");
            for (CreditAccount account : creditAccountsList) {
                accountOption.append(account);
                accountOption.append("\n----------------------------------------\n");
            }
            System.out.println(accountOption);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Выход за границы списка!");
        } catch (NegativeAmountException | ObjectCreatException e) {
            System.out.println(e.getMessage());
        }
    }
}