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
        try {
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
                        true, true, true, true, random.nextInt(1000) + 1000,
                        random.nextInt(100) + 50));
                id += 1;

                bankOfficeService.create(new BankOffice(
                        id, "Офис " + id, "Адресс " + id, bank, true,
                        true, false, false, true, random.nextInt(4000) + 1000,
                        random.nextInt(100) + 30));
                id += 1;

                bankOfficeService.create(new BankOffice(
                        id, "Офис " + id, "Адресс " + id, bank, true,
                        true, true, false, true, random.nextInt(3000) + 1000,
                        random.nextInt(100) + 200));
                id += 1;
            }

            // Создание по 2 банкомата в каждом офисе
            // 1 Работает, 1 Не работает
            List<BankOffice> bankOffices = bankOfficeService.getAllBankOffice();
            id = 1;
            for (BankOffice bankOffice : bankOffices) {
                atmService.create(new BankAtm(id, "Банкомат " + id + " офиса " + bankOffice.getName(), Status.WORK, true,
                        true, random.nextInt(1000) + 500, random.nextInt(100) + 100, bankOffice, bankOffice.getBank(), null));

                id += 1;

                atmService.create(new BankAtm(
                        id, "Банкомат " + id + " офиса " + bankOffice.getName(), Status.NOT_WORK, false,
                        true, random.nextInt(1000) + 500, random.nextInt(100) + 100, bankOffice, bankOffice.getBank(), null));

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

            //создаем 2 платежных и 2 кредитных счета в 1 банке
            //переносим 1 кредитный и 2 платежный счета во 2 файл
            try {
                paymentAccountService.create(new PaymentAccount(1, userService.getUserById(1), bankService.getBankById(1), random.nextInt(4000) + 2000));
                paymentAccountService.create(new PaymentAccount(2, userService.getUserById(2), bankService.getBankById(1), random.nextInt(4000) + 2000));
                creditAccountService.create(new CreditAccount(1, userService.getUserById(1), bankService.getBankById(1),
                        LocalDate.of(2004, random.nextInt(11) + 1, random.nextInt(27) + 1),
                        random.nextInt(10) + 1,
                        random.nextInt(2000) + 1000,
                        employeeService.getEmployeeById(1),
                        paymentAccountService.getPaymentAccountById(1)
                ));
                creditAccountService.create(new CreditAccount(2, userService.getUserById(2), bankService.getBankById(1),
                        LocalDate.of(2004, random.nextInt(11) + 1, random.nextInt(27) + 1),
                        random.nextInt(10) + 1,
                        random.nextInt(2000) + 1000,
                        employeeService.getEmployeeById(1),
                        paymentAccountService.getPaymentAccountById(2)
                ));

                bankService.saveToFile("old.txt", 1);
                bankService.transfer("old.txt", 2, 1, 2);
                bankService.saveToFile("new2.txt", 2);
                bankService.saveToFile("new1.txt", 1);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Выход за границы списка!");
        } catch (NegativeAmountException | ObjectCreatException e) {
            System.out.println(e.getMessage());
        }
    }
}