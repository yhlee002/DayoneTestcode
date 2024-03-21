package com.jyujyu.dayonetest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

public class JUnitPracticeTest {

    @Test
    public void assertEqualsTest() {
        String expect = "Something";
        String actual = "Something";

        Assertions.assertEquals(expect, actual);
    }

    @Test
    public void assertNotEquals() {
        String expect = "Something";
        String actual = "Nothing";

        Assertions.assertNotEquals(expect, actual);
    }

    @Test
    public void assertTrue() {
        Integer a = 10;
        Integer b = 10;

        Assertions.assertTrue(a.equals(b));
    }

    @Test
    public void assertFalse() {
        Integer a = 10;
        Integer b = 20;

        Assertions.assertFalse(a.equals(b));
    }

    @Test
    public void assertThrows() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException("임의로 발생시킨 에러");
        });
    }

    @Test
    public void assertNull() {
        String val = null;
        Assertions.assertNull(val);
    }

    @Test
    public void assertNotNull() {
        String val = "Hello";
        Assertions.assertNotNull(val);
    }

    @Test
    public void assertIterableEquals() {
        List<Integer> list1 = List.of(1, 2);
        List<Integer> list2 = List.of(1, 2);

        Assertions.assertIterableEquals(list1, list2);
    }

    @Test()
    public void assertAll() {
        String expect = "Something";
        String actual = "Something";

        List<Integer> list1 = List.of(1, 2);
        List<Integer> list2 = List.of(1, 2);

        Assertions.assertAll("Assert All", List.of(
                () -> { Assertions.assertEquals(expect, actual); },
                () -> { Assertions.assertIterableEquals(list1, list2); }
        ));
    }

    @Test
    public void assertTimeout() {
        Assertions.assertTimeout(Duration.ofSeconds(3), () -> {
            Thread.sleep(2000);
        });
    }

    @Test
    public void assertTimeoutPreemptively() {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(3), () -> {
            Thread.sleep(3000);
        });
    }

    @Test
    public void assertSame() {
        List<String> a = List.of("a", "b");
        List<String> b = a;

        Assertions.assertSame(a, b);
    }

    @Test
    public void assertNotSame() {
        List<String> a = List.of("a", "b");
        List<String> b = List.of("a", "b");

        Assertions.assertNotSame(a, b);
    }


}
