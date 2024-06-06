package ru.job4j.cash;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class AccountStorage {

    private final ConcurrentHashMap<Integer, Account> accounts = new ConcurrentHashMap<>();

    public boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), new Account(account.id(), account.amount())) == null;
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