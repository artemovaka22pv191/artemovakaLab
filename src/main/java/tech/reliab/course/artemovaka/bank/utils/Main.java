package tech.reliab.course.artemovaka.bank.utils;

import tech.reliab.course.artemovaka.bank.entity.*;
import tech.reliab.course.artemovaka.bank.service.*;
import tech.reliab.course.artemovaka.bank.service.impl.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        BankService bankService = new BankServiceImpl();
        Bank bank = bankService.create(new Bank(1, "БАНК1"));
        System.out.println(bank);

        BankOfficeService bankOfficeService = new BankOfficeServiceImpl();
        BankOffice bankOffice = bankOfficeService.create(new BankOffice(1, "ОФИС1", "АДРЕС ОФИСА1",
                bank, true, true, true, true, true,
                100000, 1000));
        System.out.println(bankOffice);

        EmployeeService employeeService = new EmployeeServiceImpl();
        Employee employee = employeeService.create(new Employee(1, "РАБОТНИК1", LocalDate.of(2002, 5, 6),
                "ДОЛЖНОСТЬ", true, bankOffice, true, 10000));
        System.out.println(employee);

        AtmService atmService = new AtmServiceImpl();
        BankAtm bankAtm = atmService.create(new BankAtm(1, "БАНКОМАТ", Status.WORK, true,
                true, 50000, 2000, bankOffice, bank, employee));
        System.out.println(bankAtm);

        UserService userService = new UserServiceImpl();
        User user = userService.create(new User(1, "ПОЛЬЗОВАТЕЛЬ", LocalDate.of(2000, 9,
                1), 1000, "АДРЕСРАБОТЫ", bank));
        System.out.println(user);

        PaymentAccountService paymentAccountService = new PaymentAccountServiceImpl();
        PaymentAccount paymentAccount = paymentAccountService.create(new PaymentAccount(1, user, bank, 40000));
        System.out.println(paymentAccount);

        CreditAccountService creditAccountService = new CreditAccountServiceImpl();
        CreditAccount creditAccount = creditAccountService.create(new CreditAccount(1, user, bank,
                LocalDate.of(2000, 1, 1), 4, 1000, employee, paymentAccount));
        System.out.println(creditAccount);
        System.out.println(user);
        System.out.println(bank);

    }
}