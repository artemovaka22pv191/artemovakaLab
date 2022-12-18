package tech.reliab.course.artemovaka.bank.utils;

import tech.reliab.course.artemovaka.bank.entity.Bank;

import java.util.Comparator;

public class BankComparator implements Comparator<Bank> {
    @Override
    public int compare(Bank left, Bank right) {

        if (left.getCountAtm() != right.getCountAtm()) {
            return left.getCountAtm() > right.getCountAtm() ? 1 : -1;
        }

        if (left.getCountEmployee() != right.getCountEmployee()) {
            return left.getCountEmployee() > right.getCountEmployee() ? 1 : -1;
        }

        if (left.getCountOffice() != right.getCountOffice()) {
            return left.getCountOffice() > right.getCountOffice() ? 1 : -1;
        }

        if (left.getInterestRate() != right.getInterestRate()) {
            return left.getInterestRate() < right.getInterestRate() ? 1 : -1;
        }
        return 0;
    }
}
