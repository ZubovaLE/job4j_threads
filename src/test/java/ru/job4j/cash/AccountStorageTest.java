package ru.job4j.cash;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class AccountStorageTest {

    private AccountStorage storage;

    @BeforeEach
    void setUp() {
        storage = new AccountStorage();
        storage.add(new Account(1, 100));
    }

    @Test
    void whenAddThenGetById() {
        var result = storage.getById(1);

        assertTrue(result.isPresent());
        assertEquals(100, result.get().amount());
    }

    @Test
    void whenUpdate() {
        assertTrue(storage.update(new Account(1, 200)));
        assertTrue(storage.getById(1).isPresent());
        assertEquals(200, storage.getById(1).get().amount());
    }

    @Test
    void updateWhenInvalidIdThenReturnFalse() {
        assertFalse(storage.update(new Account(5, 200)));
    }

    @Test
    void whenDelete() {
        storage.delete(1);

        assertTrue(storage.getById(1).isEmpty());
    }

    @Test
    void deleteWhenInvalidId() {
        Exception exception = assertThrows(IllegalStateException.class, () -> storage.delete(5));
        assertEquals("Account with id 5 was not found", exception.getMessage());
    }


    @Test
    void whenTransfer() {
        storage.add(new Account(2, 100));
        storage.transfer(1, 2, 100);
        var firstAccount = storage.getById(1);
        var secondAccount = storage.getById(2);

        assertTrue(firstAccount.isPresent());
        assertTrue(secondAccount.isPresent());
        assertEquals(0, firstAccount.get().amount());
        assertEquals(200, secondAccount.get().amount());
    }

    @Test
    void transferWhenNotEnoughMoneyThenGetException() {
        storage.add(new Account(2, 100));

        Exception exception = assertThrows(IllegalStateException.class, () -> storage.transfer(1, 2, 1000));
        assertEquals("Insufficient funds", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 5", "5, 2"})
    void transferWhenInvalidIdThenGetException(int fromId, int toId) {
        storage.add(new Account(2, 100));

        Exception exception = assertThrows(IllegalStateException.class, () -> storage.transfer(fromId, toId, 100));
        assertEquals("Account with id 5 was not found", exception.getMessage());
    }

}