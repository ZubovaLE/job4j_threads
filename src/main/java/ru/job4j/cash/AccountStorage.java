package ru.job4j.cash;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class AccountStorage {

    private final ConcurrentHashMap<Integer, Account> accounts = new ConcurrentHashMap<>();

    public boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public boolean update(Account account) {
        return accounts.computeIfPresent(account.id(), (id, existingAccount) -> account) != null;
    }

    public void delete(int id) {
        if (accounts.remove(id) != null) {
            System.out.println("Account with id " + id + " has been successfully deleted.");
        } else {
            throw new IllegalStateException("Account with id " + id + " was not found");
        }
    }

    public Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public boolean transfer(int fromId, int toId, int amount) {
        Account fromAccount = getById(fromId).orElseThrow(() -> new IllegalStateException("Account with id " + fromId + " was not found"));
        Account toAccount = getById(toId).orElseThrow(() -> new IllegalStateException("Account with id " + toId + " was not found"));

        synchronized (fromId < toId ? fromAccount : toAccount) {
            synchronized (fromId < toId ? toAccount : fromAccount) {
                if (fromAccount.amount() >= amount) {
                    Account newFromAccount = new Account(fromId, fromAccount.amount() - amount);
                    Account newToAccount = new Account(toId, toAccount.amount() + amount);
                    update(newFromAccount);
                    update(newToAccount);
                    return true;
                } else {
                    throw new IllegalStateException("Insufficient funds");
                }
            }
        }
    }

}