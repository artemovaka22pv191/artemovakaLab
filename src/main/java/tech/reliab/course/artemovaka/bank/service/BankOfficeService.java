package tech.reliab.course.artemovaka.bank.service;

import tech.reliab.course.artemovaka.bank.entity.*;

public interface BankOfficeService {
    /**
     * Создание офиса банка
     */
    BankOffice create(BankOffice bankOffice);

    /**
    Установка банкомата bankAtm в офисе bankOffice. При этом увеличивается количество банкоматов в соответствующем банке.
    В операции может быть отказано, если в офисе запрещена установка банкоматов.
     */
    boolean installAtm(BankOffice bankOffice, BankAtm bankAtm);

    /**
    Удаление из офиса bankOffice банкомата
    При этом уменьшается количество банкоматов в соответствующем банке.
     */
    void uninstallAtm(BankOffice bankOffice);

    /**
    Внести деньги в офис. Также деньги вносятся в соответствующий банк.
    В операции может быть отказано, если офис не работает на внос денег.
    */
    void depositMoney(BankOffice bankOffice, double sum);

    /**
    Снять деньги из офиса. Также деньги снимаются из соответствующего банка.
    В операции может быть отказано, если офис не работает на выдачу денег или в нем недостаточно денег.
    */
    void withdrawMoney(BankOffice bankOffice, double sum);

    /**
    Добавление работника в офис
    При этом увеличивается количество работников в соответствующем банке
     */
    void addEmployee(BankOffice bankOffice, Employee employee);

    /**
    Удаление работника из офиса
    При этом уменьшается количество работников в соответствующем банке
     */
    void removeEmployee(BankOffice bankOffice);
}
