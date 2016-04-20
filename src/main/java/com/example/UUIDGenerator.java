package com.example;

import java.util.UUID;

@FunctionalInterface
public interface UUIDGenerator {

    UUID generate();
}
