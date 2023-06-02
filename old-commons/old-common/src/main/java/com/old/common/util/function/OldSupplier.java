package com.old.common.util.function;

@FunctionalInterface
public interface OldSupplier<T> {
    T get() throws Throwable;
}
