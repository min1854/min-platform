package com.old.business;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

public class JunitTests {
    @Test
    public void randomSum() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        @FunctionalInterface
        interface TripleConsumer<T, U, D> {
            void accept(T t, U u, D d);
        }

        record Add(int i, int j) {

            public int sum() {
                return i + j;
            }

            public int subtraction() {
                return i - j;
            }
        }

        TripleConsumer<Integer, Integer, Boolean> tripleConsumer = new TripleConsumer<Integer, Integer, Boolean>() {
            @Override
            public void accept(Integer origin, Integer bound, Boolean flag) {
                for (int i = 0; i < 30; i++) {
                    int j = random.nextInt(origin, bound);
                    int k = random.nextInt(origin, bound);
                    while (!flag && j < k) {
                        j = random.nextInt(origin, bound);
                        k = random.nextInt(origin, bound);
                    }
                    Add e = new Add(j, k);
                    if (flag) {
                        System.out.println(j + " + " + k + " = " + e.sum());
                    } else {
                        System.out.println(j + " - " + k + " = " + e.subtraction());
                    }
                }
            }
        };


        tripleConsumer.accept(0, 10000, false);
        tripleConsumer.accept(0, 10000, true);


    }
}
