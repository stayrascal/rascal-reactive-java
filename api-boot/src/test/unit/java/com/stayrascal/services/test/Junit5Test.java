package com.stayrascal.services.test;

import static org.junit.Assert.assertNotNull;

public class Junit5Test {

    //    @ParameterizedTest
    void parameterizedTest(String word) {
        assertNotNull(word);
    }

    //    @ParameterizedTest
//    @ValueSource(strings = { "Hello", "JUnit" })
    void withValueSource(String word) {
        assertNotNull(word);
    }
}
