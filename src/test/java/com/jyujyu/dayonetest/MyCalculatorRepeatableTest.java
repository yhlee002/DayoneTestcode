package com.jyujyu.dayonetest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class MyCalculatorRepeatableTest {

    // @RepeatableTest : 인자값만큼 테스트를 반복해서 실행시켜 줌
    @DisplayName("덧셈을 5회 단순 반복 테스트")
    @RepeatedTest(5) // 현재는 동일한 내용의 코드가 5번 반복
    public void repeatedAddTest() {
        // given
        MyCalculator cal = new MyCalculator();

        // when
        cal.add(10.0);

        // then
        Assertions.assertEquals(10.0, cal.getResult());
    }

    // @ParameterizedTest : 서로 다른 매개변수 집합을 이용해 테스트 코드를 실행시켜 줌
    // @MethodSource : 매개변수 집합을 전달해줄 메서드명을 인자로 전달
    @DisplayName("매개변수를 이용한 6회 반복 테스트")
    @ParameterizedTest @MethodSource("parameterizedTestParameters")
    public void parameterizedTest(Double addVal, Double expectVal) {
        // given
        MyCalculator cal = new MyCalculator(0.0); // 가시적으로 볼 수 있게 초기화

        // when
        cal.add(addVal);

        // then
        Assertions.assertEquals(expectVal, cal.getResult());
    }

    public static Stream<Arguments> parameterizedTestParameters() {
        return Stream.of(
                Arguments.of(10.0, 10.0),
                Arguments.of(2.0, 2.0),
                Arguments.of(4.0, 4.0),
                Arguments.of(5.0, 5.0),
                Arguments.of(17.0, 17.0),
                Arguments.of(23.0, 23.0)
                );
    }

    @ParameterizedTest @MethodSource("parameterizedComplicatedCalculateTestParameters")
    public void parameterizedComplicatedCalculateTest(Double addVal, Double minusVal, Double multiplyVal, Double divideVal, Double expectVal) {
        // given
        MyCalculator cal = new MyCalculator(0.0);

        // when
        Double result = cal
                .add(addVal)
                .minus(minusVal)
                .multiply(multiplyVal)
                .divide(divideVal)
                .getResult();

        // then
        Assertions.assertEquals(expectVal, result);
    }

    public static Stream<Arguments> parameterizedComplicatedCalculateTestParameters() {
        return Stream.of(
                Arguments.of(10.0, 4.0, 2.0, 3.0, 4.0),
                Arguments.of(4.0, 2.0, 4.0, 4.0, 2.0)
        );
    }
}
