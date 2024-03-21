package com.jyujyu.dayonetest;

import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.List;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class JUnitPracticeTest {

    @Test @DisplayName("assertEquals 테스트")
    public void assertEqualsTest() {
        String expect = "Something";
        String actual = "Something";

        Assertions.assertEquals(expect, actual);
    }

    @Test @DisplayName("assertNotEquals 테스트")
    public void assertNotEquals() {
        String expect = "Something";
        String actual = "Nothing";

        Assertions.assertNotEquals(expect, actual);
    }

    @Test @DisplayName("assertTrue 테스트")
    public void assertTrue() {
        Integer a = 10;
        Integer b = 10;

        Assertions.assertTrue(a.equals(b));
    }

    @Test @DisplayName("assertFalse 테스트")
    public void assertFalse() {
        Integer a = 10;
        Integer b = 20;

        Assertions.assertFalse(a.equals(b));
    }

    @Test @DisplayName("assertThrows 테스트")
    public void assertThrows() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException("임의로 발생시킨 에러");
        });
    }

    @Test @DisplayName("assertNull 테스트")
    public void assertNull() {
        String val = null;
        Assertions.assertNull(val);
    }

    @Test @DisplayName("assertNotNull 테스트")
    public void assertNotNull() {
        String val = "Hello";
        Assertions.assertNotNull(val);
    }

    @Test @DisplayName("assertIterableEquals 테스트")
    public void assertIterableEquals() {
        List<Integer> list1 = List.of(1, 2);
        List<Integer> list2 = List.of(1, 2);

        Assertions.assertIterableEquals(list1, list2);
    }

    @Test() @DisplayName("assertAll 테스트")
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

    @Test @DisplayName("assertTimeout 테스트")
    public void assertTimeout() {
        Assertions.assertTimeout(Duration.ofSeconds(3), () -> {
            Thread.sleep(2000);
        });
    }

    @Test @DisplayName("assertTimeoutPreemptively 테스트")
    public void assertTimeoutPreemptively() {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(3), () -> {
            Thread.sleep(2000);
        });
    }

    @Test @DisplayName("assertSame 테스트")
    public void assertSame() {
        List<String> a = List.of("a", "b");
        List<String> b = a;

        Assertions.assertSame(a, b);
    }

    @Test @DisplayName("assertNotSame 테스트")
    public void assertNotSame() {
        List<String> a = List.of("a", "b");
        List<String> b = List.of("a", "b");

        Assertions.assertNotSame(a, b);
    }


}
