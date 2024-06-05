package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {

    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        synchronized (accounts) {
            return accounts.put(account.id(), account) == null;
        }
    }

    public synchronized boolean update(Account account) {
        synchronized (accounts) {
            if (getById(account.id()).isPresent()) {
                return accounts.put(account.id(), account) != null;
            }
            return false;
        }
    }

    public synchronized void delete(int id) {
        synchronized (accounts) {
            if (accounts.remove(id) != null) {
                System.out.println("Account with id " + id + " has been successfully deleted.");
            } else {
                throw new IllegalStateException("Account with id " + id + " was not found");
            }
        }
    }

    public synchronized Optional<Account> getById(int id) {
        synchronized (accounts) {
            return Optional.ofNullable(accounts.get(id));
        }
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        if (getById(fromId).isPresent() && getById(toId).isPresent()) {
            if (getById(fromId).get().amount() >= amount) {
                Account toAccount = new Account(toId, getById(toId).get().amount() + amount);
                update(toAccount);
                Account fromAccount = new Account(fromId, getById(fromId).get().amount() - amount);
                update(fromAccount);
                return true;
            } else {
                throw new IllegalStateException("Insufficient funds");
            }
        }
        throw new IllegalStateException("Account with id " + (getById(fromId).isPresent() ? toId : fromId) + " was not found");
    }

}