package ru.job4j.cas.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {

    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (id, cacheModel) -> {
            if (cacheModel.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            cacheModel.setName(model.getName());
            return new Base(id, model.getVersion() + 1);
        }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

}