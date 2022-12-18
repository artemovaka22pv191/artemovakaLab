package tech.reliab.course.artemovaka.bank.service;

import tech.reliab.course.artemovaka.bank.entity.BankOffice;
import tech.reliab.course.artemovaka.bank.entity.Employee;
import tech.reliab.course.artemovaka.bank.exceptions.NegativeAmountException;
import tech.reliab.course.artemovaka.bank.exceptions.ObjectCreatException;

import java.util.List;

public interface EmployeeService {
    /** Создание работника **/
    Employee create(Employee employee) throws NegativeAmountException, ObjectCreatException;

    /**
     * Добавление работника
     * Возвращает добавленный объект при успешном выполнении операции;
     * Если employee равен null или уже существует в массиве, возвращает null
     **/
    public Employee addEmployee(Employee employee);

    /**
     * Получение работника по id
     * Если объект не найден, возвращает null
     **/
    public Employee getEmployeeById(int employeeId);

    /**
     * Удаление работника по id
     * Возвращает true при успешном выполнении операции, иначе false
     * При удалении банка удаляются все офисы банка
     **/
    public Boolean deleteEmployeeById(int employeeId);

    /**
     * Получение всех работников
     **/
    public List<Employee> getAllEmployees();

    /**
     * Получение всех работников определенного банка
     **/
    public List<Employee> getAllEmployeeByIdBank(int bankId);

    /**
     * Получение всех работников определенного банкомата
     **/
    public List<Employee> getAllEmployeeByIdBankOffice(int bankOfficeId);

    /**
     * Вывод подробной информации о работнике
     */
    public String read(int employeeId);

    /**
    Перевод работника в другой офис.
    При этом, если работника переводят в офис другого банка, количество работников соответствующих офисов меняется.
     **/
    void transferEmployee(Employee employee, BankOffice bankOffice);

    /**
     * Возвращает всех работников работающих в officeId
     */
    List<Employee> getAllEmployeeByOfficeId(int officeId);

    /**
     * Возвращает всех работников работающих в officeId, которые могут выдавать кредиты
     */
    List<Employee> getEmployeeInOfficeWhichCanApplyLoan(int officeId);
}
