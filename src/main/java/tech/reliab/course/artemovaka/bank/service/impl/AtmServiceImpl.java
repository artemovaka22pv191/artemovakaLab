package tech.reliab.course.artemovaka.bank.service.impl;

import tech.reliab.course.artemovaka.bank.entity.Bank;
import tech.reliab.course.artemovaka.bank.entity.BankAtm;
import tech.reliab.course.artemovaka.bank.entity.BankOffice;
import tech.reliab.course.artemovaka.bank.entity.Employee;
import tech.reliab.course.artemovaka.bank.exceptions.AtmIssuingMoneyException;
import tech.reliab.course.artemovaka.bank.exceptions.NegativeAmountException;
import tech.reliab.course.artemovaka.bank.exceptions.ObjectCreatException;
import tech.reliab.course.artemovaka.bank.exceptions.ShortageMoneyException;
import tech.reliab.course.artemovaka.bank.service.AtmService;
import tech.reliab.course.artemovaka.bank.service.BankOfficeService;
import tech.reliab.course.artemovaka.bank.utils.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AtmServiceImpl implements AtmService {

    private static AtmServiceImpl INSTANCE;
    private final Map<Integer, BankAtm> bankAtms = new HashMap<>();


    public static AtmServiceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AtmServiceImpl();
        }

        return INSTANCE;
    }

    private final BankOfficeService bankOfficeService = BankOfficeServiceImpl.getInstance();

    @Override
    public BankAtm create(BankAtm bankAtm) throws NegativeAmountException, ObjectCreatException {
        if (bankAtm != null) {
            if (bankAtm.getId() < 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            if (bankAtm.getMoney() < 0) {
                throw new NegativeAmountException();
            }
            if (bankAtm.getServicePrice() < 0) {
                throw new NegativeAmountException();
            }
            if (bankAtm.getBankOffice() == null) {
                throw new ObjectCreatException("банкомат", "офис");
            }
            return addBankAtm(new BankAtm(bankAtm));
        }
        return null;
    }

    @Override
    public BankAtm addBankAtm(BankAtm bankAtm) throws NegativeAmountException, ObjectCreatException {
        if (!bankAtms.containsKey(bankAtm.getId())) {
            BankOffice bankOffice = bankAtm.getBankOffice();
            if (bankOffice != null) {
                if (bankOfficeService.addAtm(bankOffice.getId(), bankAtm)) {
                    bankAtms.put(bankAtm.getId(), bankAtm);
                    return bankAtms.get(bankAtm.getId());
                }
            } else {
                throw new ObjectCreatException("офис", "банк");
            }
        } else {
            System.out.println("Нельзя добавить офис: офис с таким id уже существует");
        }
        return null;
    }

    @Override
    public BankAtm getBankAtmById(int id) {
        if (!bankAtms.containsKey(id)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return bankAtms.get(id);
    }

    @Override
    public List<BankAtm> getAllBankAtm() {
        return new ArrayList<BankAtm>(bankAtms.values());
    }

    @Override
    public List<BankAtm> getAllBankAtmByIdBankOffice(int id) {
        List<BankAtm> bankAtmByOfficeId = bankAtms.values().stream().filter(
                atm -> atm.getBankOffice().getId() == id).toList();

        return bankAtmByOfficeId;
    }

    @Override
    public Boolean deleteBankAtm(int bankAtmId) throws NegativeAmountException {
        BankAtm bankAtm = bankAtms.get(bankAtmId);
        if (bankAtm != null) {
            if (bankOfficeService.deleteAtm(bankAtm.getBankOffice().getId(), bankAtmId)) {
                return bankAtms.remove(bankAtmId) != null;
            }
        }
        return false;
    }

    @Override
    public String read(int bankAtmId) {
        BankAtm bankAtm = getBankAtmById(bankAtmId);
        if (bankAtm != null) {
            return bankAtm.toString();
        }
        return "";
    }

    @Override
    public void depositMoney(int bankAtmId, double sum) throws NegativeAmountException {
        BankAtm bankAtm = getBankAtmById(bankAtmId);
        if (sum < 0) {
            throw new NegativeAmountException();
        }
        if ((bankAtm != null) && (bankAtm.getBankOffice() != null) && (bankAtm.getBank() != null)) {
            if (bankAtm.getStatus() != Status.NOT_WORK) {
                if (bankAtm.getIsPayInMoney()) {
                    BankOffice office = bankAtm.getBankOffice();
                    Bank bank = bankAtm.getBank();
                    double newSum = bankAtm.getMoney() + sum;
                    bankAtm.setMoney(newSum);
                    office.setMoney(office.getMoney() + newSum);
                    bank.setMoney(bank.getMoney() + newSum);
                } else {
                    System.out.println("В банкомат " + bankAtm.getName() + " нельзя вносить деньги\n");
                }
            } else {
                System.out.println("Банкомат " + bankAtm.getName() + " не работает\n");
            }
        }
    }

    @Override
    public void withdrawMoney(int bankAtmId, double sum) throws ShortageMoneyException, NegativeAmountException, AtmIssuingMoneyException {
        if (sum < 0) {
            throw new NegativeAmountException();
        }
        BankAtm bankAtm = getBankAtmById(bankAtmId);
        if ((bankAtm != null) && (bankAtm.getBankOffice() != null) && (bankAtm.getBank() != null)) {
            if (bankAtm.getStatus() == Status.WORK) {
                if (bankAtm.getIsGiveMoney()) {
                    if (bankAtm.getMoney() >= sum) {
                        BankOffice office = bankAtm.getBankOffice();
                        Bank bank = bankAtm.getBank();
                        double newSum = bankAtm.getMoney() - sum;
                        bankAtm.setMoney(newSum);
                        office.setMoney(office.getMoney() + newSum);
                        bank.setMoney(bank.getMoney() + newSum);
                    } else {
                        throw new ShortageMoneyException(bankAtms.get(bankAtmId));
                    }
                } else {
                    throw new AtmIssuingMoneyException(bankAtm);
                }
            }
        }
    }

    @Override
    public void setEmplByBankAtm(int bankAtmId, Employee empl) {
        if (!bankAtms.containsKey(bankAtmId)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        BankAtm bankAtm = bankAtms.get(bankAtmId);
        bankAtm.setEmployee(empl);
    }

    @Override
    public void updateBank(int id, Bank bank) {
        if (!bankAtms.containsKey(id)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        BankAtm bankAtm = bankAtms.get(id);
        bankAtm.setBank(bank);
    }

    @Override
    public List<BankAtm> getAllBankAtmByEmplId(int emplId) {
        return bankAtms.values().stream().toList().stream()
                .filter(atm -> atm.getEmployee().getId() == emplId)
                .toList();
    }


    @Override
    public List<BankAtm> getAllBankAtmByOfficeId(int offId) {
        return bankAtms.values().stream().toList().stream()
                .filter(atm -> atm.getBankOffice().getId() == offId)
                .toList();
    }


}
