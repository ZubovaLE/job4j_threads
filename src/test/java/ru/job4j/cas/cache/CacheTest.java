package ru.job4j.cas.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheTest {

    private Cache cache;

    @BeforeEach
    void setCache() {
        cache = new Cache();
    }

    @Test
    void addWhenNotAbsentTheReturnFalse() {
        cache.add(new Base(1, 1));

        assertFalse(cache.add(new Base(1, 1)));
    }

    @Test
    void whenUpdateSuccess() {
        Base base = new Base(1, 0);
        base.setName("old name");
        cache.add(base);
        Base updatedBase = new Base(1, 0);
        updatedBase.setName("new name");

        assertTrue(cache.update(updatedBase));
        assertEquals("new name", cache.get(1).getName());
    }

    @Test
    public void whenUpdateFail() {
        Base model = new Base(1, 0);
        model.setName("name");
        cache.add(model);
        Base updatedModel = new Base(1, 1);
        updatedModel.setName("newName");

        Exception exception = assertThrows(OptimisticException.class, () ->  cache.update(updatedModel));
        assertEquals("Versions are not equal", exception.getMessage());
    }

    @Test
    public void whenDeleteSuccess() {
        Base base = new Base(1, 0);
        cache.add(base);
        cache.delete(base);
        assertNull(cache.get(1));
    }

}