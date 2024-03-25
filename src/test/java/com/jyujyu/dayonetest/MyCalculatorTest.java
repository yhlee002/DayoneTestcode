package com.jyujyu.dayonetest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MyCalculatorTest {

  @Test
  @DisplayName("MyCalculator 덧셈 테스트")
  void addTest() {
    // AAA Pattern
    // Arrange - 준비
    MyCalculator cal = new MyCalculator();

    // Act - 행동
    cal.add(10.0);

    // Assert - 단언/검증
    Assertions.assertEquals(10.0, cal.getResult());
  }

  @Test
  @DisplayName("MyCalculator 뺄셈 테스트")
  void minus() {
    // GWT pattern
    // Given - 준비(= Arrange)
    MyCalculator cal = new MyCalculator(10.0);

    // When - 행동/연산(= Act)
    cal.minus(5.0);

    // Then - 검증(= Assert)
    Assertions.assertEquals(5.0, cal.getResult());
  }

  @Test
  @DisplayName("MyCalculator 곱셈 테스트")
  void multiply() {
    MyCalculator cal = new MyCalculator(2.0);

    cal.multiply(2.0);

    Assertions.assertEquals(4.0, cal.getResult());
  }

  @Test
  @DisplayName("MyCalculator 나눗셈 테스트")
  void divide() {
    // given
    MyCalculator cal = new MyCalculator(10.0);

    // when
    cal.divide(2.0);

    // then
    Assertions.assertEquals(5.0, cal.getResult());
  }

  @Test
  @DisplayName("MyCalculator 사칙연산 테스트")
  void complicatedCalculateTest() {
    // given
    MyCalculator cal = new MyCalculator(0.0);

    // when
    Double result = cal.add(10.0).minus(4.0).multiply(2.0).divide(3.0).getResult();

    // then
    Assertions.assertEquals(4.0, result);
  }

  @Test
  @DisplayName("MyCalculator 0으로 나누었을 때 ZeroDivisionException 발생 테스트")
  void divideZeroTest() {
    // given
    MyCalculator cal = new MyCalculator(10.0);

    // when & then
    Assertions.assertThrows(
        MyCalculator.ZeroDivisionException.class,
        () -> {
          cal.divide(0.0);
        });
  }
}
