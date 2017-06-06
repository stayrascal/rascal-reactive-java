package com.stayrascal.services.test;

import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class SafeAdditionUtilTest {
    private SafeAdditionUtil serviceUnderTest = new SafeAdditionUtil();

    @Test
    @Parameters({
            "1, 2, 3",
            "-10, 30, 20",
            "15, -5, 10",
            "-5, -10, -15"})
    public void whenWithAnnotationProvidedParams_thenSafeAdd(
            int a, int b, int expectedValue) {

        assertEquals(expectedValue, serviceUnderTest.safeAdd(a, b));
    }

    @Test
    @Parameters(source = TestDataProvider.class)
    public void whenWithNamedClass_thenSafeAdd(
            int a, int b, int expectedValue) {

        assertEquals(expectedValue, serviceUnderTest.safeAdd(a, b));
    }

    @Test
    @FileParameters("api-boot/src/test/unit/resources/JunitParamsTestParameters.csv")
    public void whenWithCsvFile_thenSafeAdd(
            int a, int b, int expectedValue) {

        assertEquals(expectedValue, serviceUnderTest.safeAdd(a, b));
    }

    @Test
    @Parameters(method = "parametersToTestAdd")
    public void whenWithNamedMethod_thenSafeAdd(
            int a, int b, int expectedValue) {

        assertEquals(expectedValue, serviceUnderTest.safeAdd(a, b));
    }

    private Object[] parametersToTestAdd() {
        return new Object[]{
                new Object[]{1, 2, 3},
                new Object[]{-10, 30, 20},
                new Object[]{Integer.MAX_VALUE, 2, Integer.MAX_VALUE},
                new Object[]{Integer.MIN_VALUE, -8, Integer.MIN_VALUE}
        };
    }
}
