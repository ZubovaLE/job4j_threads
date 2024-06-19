package ru.job4j.cas.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class Base {

    private final int id;
    private final int version;
    @Setter
    private String name;

}