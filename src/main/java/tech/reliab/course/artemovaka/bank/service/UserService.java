package tech.reliab.course.artemovaka.bank.service;

import tech.reliab.course.artemovaka.bank.entity.Bank;
import tech.reliab.course.artemovaka.bank.entity.CreditAccount;
import tech.reliab.course.artemovaka.bank.entity.PaymentAccount;
import tech.reliab.course.artemovaka.bank.entity.User;
import tech.reliab.course.artemovaka.bank.exceptions.NegativeAmountException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface UserService {

    // Создание пользователя
    User create(User user) throws NegativeAmountException;

    /**
     * Добавление пользователя
     *
     **/
    User addUser(User user);

    /**
     * Получение пользователя по id
     **/
    User getUserById(int userId);

    /**
     * Удаление работника по id
     **/
    boolean deleteUserById(int userId);

    /**
     * Получение всех работников
     **/
    List<User> getAllUsers();

    /**
     * Получение всех пользователей определенного банка
     **/
    public List<User> getAllUserByIdBank(int bankId);

    /**
     * Добавление кредитного аккаунта
     * Увеличивает кол-во кредитных аккаунтов
     */
    boolean addCreditAccount(int userId, CreditAccount creditAccount);

    /**
     * Удаление клиента банка по ID
     **/
    boolean deleteCreditAccount(int userId, int creditAccountId);

    /**
     * Добавление платежного аккаунта
     * Увеличивает кол-во платежных аккаунтов
     */
    boolean addPaymentAccount(int userId, PaymentAccount paymentAccount);

    /**
     * Удаление клиента банка по ID
     **/
    boolean deletePaymentAccount(int userId, int paymentAccountId);

    /**
     * Вывод подробной информации о работнике
     */
    public String read(int userId);

    /**
    Регистрация места работы. При этом подсчитывается кредитный рейтинг пользователя.
     **/
    void jobRegistration(User user, String addressJob, double monthIncome);

    /**
    Подсчет кредитного рейтинга пользователя (генерируется рандомно
    исходя из ежемесячного дохода, от меньше 1 000 – 100, от 1 000 до 2 000 – 200 и т.д. вплоть до 10 000 )
     **/
    void calculateRating(User user);

    /**
     Вывод всех счетов bank
     **/
    void saveToFile(String fileName, int bankId) throws IOException;

    /**
     Перенос счетов creditAccountId, payAccountId из файла fileName
     в банк toBankId
     Если данные счет не найден, перенос не происходит
     Если creditAccountId = -1 кредитный аккаунт не переносится
     Если payAccountId = -1 платежный аккаунт не переносится
     **/
    void transfer(String fileName, int toBankId, int creditAccountId, int payAccountId) throws IOException;
    }
