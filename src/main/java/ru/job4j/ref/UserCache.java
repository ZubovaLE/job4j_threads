package ru.job4j.ref;

import javax.annotation.concurrent.ThreadSafe;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class UserCache {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public void add(User user) {
        users.put(id.incrementAndGet(), User.of(user.getName()));
    }

    public User findById(int id) {
        if (!users.containsKey(id)) {
            throw new NoSuchElementException();
        }
        return User.of(users.get(id).getName());
    }

    public List<User> findAll() {
        return users.values().stream()
                .map(u -> User.of(u.getName()))
                .toList();
    }

}