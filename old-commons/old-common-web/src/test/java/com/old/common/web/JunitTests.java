package com.old.common.web;

import org.junit.jupiter.api.Test;

public class JunitTests {

    @Test
    public void className() {
        // class com.old.common.web.JunitTests
        System.out.println(JunitTests.class.toString());
        // public class com.old.common.web.JunitTests
        System.out.println(JunitTests.class.toGenericString());
        // com.old.common.web.JunitTests
        System.out.println(JunitTests.class.getName());
    }
}
