package tech.reliab.course.artemovaka.bank.service.impl;

import com.google.gson.Gson;
import tech.reliab.course.artemovaka.bank.entity.*;
import tech.reliab.course.artemovaka.bank.entity.jsonClasses.JsonCreditAccount;
import tech.reliab.course.artemovaka.bank.entity.jsonClasses.JsonPayAccount;
import tech.reliab.course.artemovaka.bank.exceptions.NegativeAmountException;
import tech.reliab.course.artemovaka.bank.service.BankService;
import tech.reliab.course.artemovaka.bank.service.UserService;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import java.io.*;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl INSTANCE;
    private final Map<Integer, User> users = new HashMap<>();
    Type payAccArrType = new TypeToken<ArrayList<JsonPayAccount>>() {
    }.getType();
    Type credAccArrType = new TypeToken<ArrayList<JsonCreditAccount>>() {
    }.getType();

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
    public User create(User user) throws NegativeAmountException {
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
    public User addUser(User user) {
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
    public User getUserById(int userId) {
        var user = users.get(userId);
        ;
        if (user == null) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return users.get(userId);
    }

    @Override
    public boolean deleteUserById(int userId) {
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

    // сериализация всех платежных аккаунтов банка bankID
    private List<JsonPayAccount> makeJsonPaymentAcc(int bankID) {
        List<JsonPayAccount> jsonPayment = new ArrayList<>();
        var payAccounts = PaymentAccountServiceImpl.getInstance().getAllPaymentAccounts();
        for (PaymentAccount paymentAccount : payAccounts) {
            if (Objects.equals(paymentAccount.getBank().getId(), bankID)) {
                jsonPayment.add(new JsonPayAccount(paymentAccount));
            }
        }
        return jsonPayment;
    }

    // сериализация всех кредитных аккаунтов банка bankID
    private List<JsonCreditAccount> makeJsonCreditAcc(Integer bankID) {
        List<JsonCreditAccount> jsonCredit = new ArrayList<>();
        var creditAccounts = CreditAccountServiceImpl.getInstance().getAllCreditAccount();
        for (CreditAccount creditAccount : creditAccounts) {
            if (Objects.equals(creditAccount.getBank().getId(), bankID)) {
                jsonCredit.add(new JsonCreditAccount(creditAccount));
            }
        }
        return jsonCredit;
    }

    @Override
    public void saveToFile(String fileName, int bankId) throws IOException {
        Gson gson = new Gson();
        String payAccStr = gson.toJson(makeJsonPaymentAcc(bankId));
        String creditAccStr = gson.toJson(this.makeJsonCreditAcc(bankId));
        File file = new File(fileName);
        FileWriter writer = new FileWriter(file);
        writer.write("Платёжные счета:\n" + payAccStr + "\n\nКредитные счета:\n" + creditAccStr);
        writer.close();
    }

    @Override
    public void transfer(String fileName, int toBankId, int creditAccountId, int payAccountId) throws IOException {
        //this.saveToFile(fileName, fromBankId);
        Gson gson = new Gson();
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);
        String line = reader.readLine();
        boolean first = true;
        while (line != null) {
            if (!line.isEmpty()) {
                if (line.charAt(0) == '[') {
                    if (first) {
                        first = false;
                        this.toPayAccount(gson.fromJson(line, payAccArrType), payAccountId, toBankId);
                    } else {
                        this.toCreditAccount(gson.fromJson(line, credAccArrType), creditAccountId, toBankId);
                    }
                }
            }
            line = reader.readLine();
        }
        fr.close();
    }

    private void toPayAccount(ArrayList<JsonPayAccount> jsonPayAcc, int PayId, int toBankId) {
        if (PayId != -1) {
            PaymentAccount payAcc = PaymentAccountServiceImpl.getInstance().getPaymentAccountById(PayId);
            if (!jsonPayAcc.isEmpty()) {
                for (int i = 0; i < jsonPayAcc.size(); i++) {
                    if (jsonPayAcc.get(i).getId() == PayId) {
                        jsonPayAcc.get(i).setBankID(toBankId);
                        PaymentAccountServiceImpl.getInstance().deletePaymentAccountById(PayId);
                        PaymentAccountServiceImpl.getInstance().addPaymentAccount(new PaymentAccount(jsonPayAcc.get(i)));
                    }
                }
            }
        }
    }

    private void toCreditAccount(ArrayList<JsonCreditAccount> jsonCreditAcc, int CreditId, int toBankId) {
        if (CreditId != -1) {
            CreditAccount CreditAcc = CreditAccountServiceImpl.getInstance().getCreditAccountById(CreditId);
            if (!jsonCreditAcc.isEmpty()) {
                for (int i = 0; i < jsonCreditAcc.size(); i++) {
                    if (jsonCreditAcc.get(i).getId() == CreditId) {
                        jsonCreditAcc.get(i).setBankID(toBankId);
                        CreditAccountServiceImpl.getInstance().deleteCreditAccountById(CreditId);
                        CreditAccountServiceImpl.getInstance().addCreditAccount(new CreditAccount(jsonCreditAcc.get(i)));
                    }
                }
            }
        }
    }

}
