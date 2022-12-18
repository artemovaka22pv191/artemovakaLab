package tech.reliab.course.artemovaka.bank.service.impl;

import tech.reliab.course.artemovaka.bank.entity.*;
import tech.reliab.course.artemovaka.bank.exceptions.NegativeAmountException;
import tech.reliab.course.artemovaka.bank.service.PaymentAccountService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentAccountServiceImpl implements PaymentAccountService {
    private static PaymentAccountServiceImpl INSTANCE;
    private final Map<Integer, PaymentAccount> paymentAccounts = new HashMap<>();

    private PaymentAccountServiceImpl() {
    }

    public static PaymentAccountServiceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PaymentAccountServiceImpl();
        }

        return INSTANCE;
    }

    UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public PaymentAccount create(PaymentAccount paymentAccount) throws NegativeAmountException {
        if (paymentAccount != null) {
            if (paymentAccount.getId() < 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            if (paymentAccount.getMoney() < 0) {
                throw new NegativeAmountException();
            }
            return addPaymentAccount(new PaymentAccount(paymentAccount));
        }
        return null;
    }

    @Override
    public PaymentAccount addPaymentAccount(PaymentAccount paymentAccount) {
        if (paymentAccount != null) {
            if (!paymentAccounts.containsKey(paymentAccount.getId())) {
                User user = paymentAccount.getUser();
                if (user != null) {
                    if (userService.addPaymentAccount(user.getId(), paymentAccount)) {
                        paymentAccounts.put(paymentAccount.getId(), paymentAccount);
                        return paymentAccounts.get(paymentAccount.getId());
                    }
                }
            }
        }

        return null;
    }

    @Override
    public PaymentAccount getPaymentAccountById(int paymentAccountId) {
        var paymentAccount = paymentAccounts.get(paymentAccountId);
        if (paymentAccount == null) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return paymentAccount;
    }

    @Override
    public boolean deletePaymentAccountById(int paymentAccountId) {
        PaymentAccount paymentAccount = paymentAccounts.get(paymentAccountId);
        if (paymentAccount != null) {
            if (userService.deletePaymentAccount(paymentAccount.getUser().getId(), paymentAccountId)) {
                return paymentAccounts.remove(paymentAccountId) != null;
            }
        }
        return false;
    }

    @Override
    public List<PaymentAccount> getAllPaymentAccounts() {
        return new ArrayList<PaymentAccount>(paymentAccounts.values());
    }

    @Override
    public void depositMoney(PaymentAccount account, double sum) throws NegativeAmountException {
        if (sum < 0) {
            throw new NegativeAmountException();
        }
        if (account != null) {
            account.setMoney(account.getMoney() + sum);
        }
    }

    @Override
    public void withdrawMoney(PaymentAccount account, double sum)throws NegativeAmountException  {
        if (sum < 0) {
            throw new NegativeAmountException();
        }
        if (account != null) {
            if (account.getMoney() >= sum) {
                account.setMoney(account.getMoney() - sum);
            } else {
                System.out.println("На аккаунте не достаточно денег для снятия\n");
            }
        }
    }

    @Override
    public List<PaymentAccount> getAllPaymentAccountByIdUser(int userId) {
        List<PaymentAccount> paymentAccountsByUser = paymentAccounts.values().stream().filter(
                paymentAccount -> paymentAccount.getUser().getId() == userId).toList();

        return paymentAccountsByUser;
    }

    @Override
    public String read(int paymentAccountId) {
        PaymentAccount paymentAccount = paymentAccounts.get(paymentAccountId);
        if (paymentAccount != null) {
            return paymentAccount.toString();
        }
        return "";
    }

    @Override
    public PaymentAccount paymentAccountRegistration(Bank bank, User user, double money)throws NegativeAmountException {
        if (money < 0) {
            throw new NegativeAmountException();
        }
        if (paymentAccounts.values().stream().filter(account -> account.getUser().getId() == user.getId() &&
                account.getBank().getId() == bank.getId()).toList().size() != 0) {
            return paymentAccounts.values().stream().filter(account -> account.getUser().getId() == user.getId() &&
                    account.getBank().getId() == bank.getId()).toList().get(0);
        } else {
            var account = new PaymentAccount(paymentAccounts.size(), user, bank, money);
            this.addPaymentAccount(account);
            BankServiceImpl.getInstance().addClient(bank.getId(), user);
            return account;
        }
    }

}
