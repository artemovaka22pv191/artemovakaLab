package tech.reliab.course.artemovaka.bank.service.impl;

import tech.reliab.course.artemovaka.bank.entity.*;
import tech.reliab.course.artemovaka.bank.exceptions.NegativeAmountException;
import tech.reliab.course.artemovaka.bank.service.BankService;
import tech.reliab.course.artemovaka.bank.service.UserService;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl INSTANCE;
    private final Map<Integer, User> users = new HashMap<>();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserServiceImpl();
        }

        return INSTANCE;
    }

    private final BankService bankService = BankServiceImpl.getInstance();

    @Override
    public User create(User user)throws NegativeAmountException{
        if (user != null) {
            if (user.getId() < 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            if (!((user.getMonthIncome() > 0) && (user.getMonthIncome() <= 10000))) {
                throw new NegativeAmountException();
            }
            calculateRating(user);
            return addUser(new User(user));
        }
        return null;
    }

    @Override
    public User addUser(User user){
        if (user != null) {
            if (!users.containsKey(user.getId())) {
                List<Bank> banks = user.getBanks();
                if (banks != null) {
                    for (Bank bank : banks) {
                        bankService.addClient(bank.getId(), user);
                    }
                    users.put(user.getId(), user);
                    return users.get(user.getId());
                }
            }
        }
        return null;
    }

    @Override
    public User getUserById(int userId)throws ArrayIndexOutOfBoundsException{
        var user = users.get(userId);;
        if(user == null){
            throw new ArrayIndexOutOfBoundsException("Пользователя с таким id не существует");
        }
        return users.get(userId);
    }

    @Override
    public boolean deleteUserById(int userId){
        User user = users.get(userId);
        if (user != null) {
            PaymentAccountServiceImpl paymentAccountService = PaymentAccountServiceImpl.getInstance();
            CreditAccountServiceImpl creditAccountService = CreditAccountServiceImpl.getInstance();

            List<PaymentAccount> paymentAccounts = paymentAccountService.getAllPaymentAccountByIdUser(userId);
            // Удаляем все платежные аккаунты
            for (PaymentAccount paymentAccount : paymentAccounts) {
                if (!paymentAccountService.deletePaymentAccountById(paymentAccount.getId())) {
                    System.out.println("Не удалось удалить пользователя из-за платежного аккаунта");
                    return false;
                }
            }

            List<CreditAccount> creditAccounts = creditAccountService.getAllCreditAccountByIdUser(userId);
            // Удаляем все кредитные аккаунты
            for (CreditAccount creditAccount : creditAccounts) {
                if (!creditAccountService.deleteCreditAccountById(creditAccount.getId())) {
                    System.out.println("Не удалось удалить пользователя из-за кредитного аккаунта");
                    return false;
                }
            }

            for (Bank bank : user.getBanks()) {
                bankService.deleteClient(bank.getId(), userId);
            }
            return users.remove(userId) != null;
        }
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<User>(users.values());
    }

    @Override
    public List<User> getAllUserByIdBank(int bankId) {
        List<User> usersBank = new ArrayList<>();
        for (User user : users.values()) {
            if (user.getBanks().stream().filter(bank -> bank.getId() == bankId).toList().size() != 0) {
                usersBank.add(user);
            }
        }
        return usersBank;
    }

    @Override
    public boolean addCreditAccount(int userId, CreditAccount creditAccount) {
        User user = users.get(userId);
        if (user != null) {
            user.setCountCreditAccount(user.getCountCreditAccount() + 1);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCreditAccount(int userId, int creditAccountId) {
        User user = users.get(userId);
        if (user != null) {
            int count = user.getCountCreditAccount();
            if (count == 0) {
                System.out.println("Нельзя удалить кредитный аккаунта");
            } else {
                user.setCountCreditAccount(count - 1);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addPaymentAccount(int userId, PaymentAccount paymentAccount) {
        User user = users.get(userId);
        if (user != null) {
            user.setCountPaymentAccount(user.getCountPaymentAccount() + 1);
            return true;
        }
        return false;
    }

    @Override
    public boolean deletePaymentAccount(int userId, int paymentAccountId) {
        User user = users.get(userId);
        if (user != null) {
            int count = user.getCountPaymentAccount();
            if (count == 0) {
                System.out.println("Нельзя удалить платежный аккаунт");
            } else {
                user.setCountPaymentAccount(count - 1);
                return true;
            }
        }
        return false;
    }

    @Override
    public String read(int userId) {
        User user = users.get(userId);
        if (user != null) {
            StringBuilder str = new StringBuilder(
                    "ID пользователя: " + user.getId() + "\n" +
                            "Имя пользователя: " + user.getName() + " \n" +
                            "Дата рождения: " + user.getDateBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n" +
                            "Работа: " + user.getAddressJob() + "\n" +
                            "Ежемесячный доход: " + String.format("%.4f", user.getMonthIncome()) + "\n" +
                            "Кол-во кредитных аккаунтов: " + user.getCountCreditAccount() + "\n" +
                            "Кол-во платежных аккаунтов: " + user.getCountPaymentAccount() + "\n" +
                            "Кредитный рейтинг: " + user.getCreditRating() + "\n");

            str.append((user.getCountCreditAccount() > 0) ? "Информация о кредитных аккаунтах :\n" : "");
            CreditAccountServiceImpl creditAccountService = CreditAccountServiceImpl.getInstance();
            PaymentAccountServiceImpl paymentAccountService = PaymentAccountServiceImpl.getInstance();

            List<CreditAccount> creditAccounts = creditAccountService.getAllCreditAccountByIdUser(userId);
            for (CreditAccount creditAccount : creditAccounts) {
                str.append("..............................................................\n");
                str.append(creditAccountService.read(creditAccount.getId()));
                str.append("..............................................................\n");
            }

            List<PaymentAccount> paymentAccounts = paymentAccountService.getAllPaymentAccountByIdUser(userId);
            for (PaymentAccount paymentAccount : paymentAccounts) {
                str.append("..............................................................\n");
                str.append(paymentAccountService.read(paymentAccount.getId()));
                str.append("..............................................................\n");
            }

            return str.toString();
        }
        return "";
    }

    @Override
    public void jobRegistration(User user, String addressJob, double monthIncome) {
        if (user != null) {
            user.setAddressJob(addressJob);
            user.setMonthIncome(monthIncome);
            calculateRating(user);
        }
    }

    @Override
    public void calculateRating(User user) {
        int rate = 100;
        BigDecimal value = BigDecimal.valueOf(1000L);
        while (BigDecimal.valueOf(user.getMonthIncome()).compareTo(value) > 0) {
            rate += 100;
            value = value.add(BigDecimal.valueOf(1000L));
        }
        user.setCreditRating(rate);

    }
}
