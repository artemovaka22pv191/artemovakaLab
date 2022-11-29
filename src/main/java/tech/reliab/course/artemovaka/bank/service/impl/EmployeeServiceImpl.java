package tech.reliab.course.artemovaka.bank.service.impl;

import tech.reliab.course.artemovaka.bank.entity.Bank;
import tech.reliab.course.artemovaka.bank.entity.BankOffice;
import tech.reliab.course.artemovaka.bank.entity.Employee;
import tech.reliab.course.artemovaka.bank.service.BankService;
import tech.reliab.course.artemovaka.bank.service.EmployeeService;

import java.util.Objects;

public class EmployeeServiceImpl implements EmployeeService {
    BankService bankService = new BankServiceImpl();

    @Override
    public Employee create(Employee employee) {
        if (employee != null) {
            if (employee.getOffice() == null) {
                System.out.println("Ошибка! Нельзя создать работника без офиса");
                return null;
            }
            bankService.addEmployee(employee.getOffice().getBank(), employee);
            return new Employee(employee);
        }
        return null;
    }

    @Override
    public void transferEmployee(Employee employee, BankOffice bankOffice) {
        if ((employee != null) && (bankOffice != null) && (employee.getBank() != null) && (bankOffice.getBank() != null)) {
            employee.setOffice(bankOffice);
            Bank currentBank = employee.getBank();
            Bank purposeBank = bankOffice.getBank();
            if (!Objects.equals(currentBank.getId(), purposeBank.getId())) {
                employee.setBank(purposeBank);
                currentBank.setCountEmployee(currentBank.getCountEmployee() - 1);
                purposeBank.setCountEmployee(purposeBank.getCountEmployee() + 1);
            }
        }
    }
}
