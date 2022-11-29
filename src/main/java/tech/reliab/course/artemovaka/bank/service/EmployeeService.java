package tech.reliab.course.artemovaka.bank.service;

import tech.reliab.course.artemovaka.bank.entity.BankOffice;
import tech.reliab.course.artemovaka.bank.entity.Employee;

public interface EmployeeService {
    /**
     * Создание работника
     */
    Employee create(Employee employee);

    /**
     * Перевод работника в другой офис.
     * При этом, если работника переводят в офис другого банка, количество работников соответствующих офисов меняется.
     */
    void transferEmployee(Employee employee, BankOffice bankOffice);
}
