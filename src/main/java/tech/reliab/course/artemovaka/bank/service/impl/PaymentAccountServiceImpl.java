package tech.reliab.course.artemovaka.bank.service.impl;

import tech.reliab.course.artemovaka.bank.entity.PaymentAccount;
import tech.reliab.course.artemovaka.bank.service.PaymentAccountService;

public class PaymentAccountServiceImpl implements PaymentAccountService {
    @Override
    public PaymentAccount create(PaymentAccount paymentAccount) {
        if (paymentAccount != null) {
            return new PaymentAccount(paymentAccount);
        }
        return null;
    }

    @Override
    public void depositMoney(PaymentAccount account, double sum) {
        if (account != null) {
            account.setMoney(account.getMoney() + sum);
        }
    }

    @Override
    public void withdrawMoney(PaymentAccount account, double sum) {
        if (account != null) {
            if (account.getMoney() >= sum) {
                account.setMoney(account.getMoney() - sum);
            } else {
                System.out.println("На аккаунте не достаточно денег для снятия\n");
            }
        }
    }
}
