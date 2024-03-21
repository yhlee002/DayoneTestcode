# JUnit : 테스트의 시작

## 1. Test Code 구조

### AAA Pattern & GWT Pattern
- Arrange : 준비
- Act : 실행
- Assert : 단언/검증

> 💡 **GWT Pattern** : Given - When - Then 단계를 거치는 패턴으로, AAA의 각 단계와 대응된다.

## 2. Assertions
- `Assertions.assertEquals(T expected, T actual)` : 값 검증
- `Assertions.assertThrows(T expected, Executable e)` : Exception 검증

```java
// given
Double num = 0.0;

// when & then
Assertions.assertThrows(RuntimeException.class, () -> {
    Double val = 1.0;
    val /= num;
});
```

### Assertions 주요 메서드

- `assertEquals(Class<T> expect, Class<T> actual)` 두 값이 동일함을 확인
- `assertNotEquals(Class<T> expect, Class<T> actual)` 두 값이 다름을 확인
- `assertTrue(Boolean actual)` 조건이 참임을 확인
- `assertFalse(Boolean actual)` 조건이 거짓임을 확인
- `assertNull(Boolean actual)` 값이 null임을 확인
- `assertNotNull()` 값이 null이 아님을 확인
- `assertThrows(Class<T> expectExcpetion, Executable e)` 특정 예외가 발생함을 확인
- `assertIterableEquals()` 두 Iterable 객체가 동일한 요소를 포함하고, 요소들의 순서가 일치함을 확인

```java
List<Integer> list1 = List.of(1, 2, 3);
List<Integer> list2 = List.of(1, 2, 3);
    
Assertions.assertIterableEquals(list1, list2);
```

- `assertAll()` 여러 검증(assertion)을 그룹화하여 모두 실행. 중간에 실패하더라도 나머지 검증들을 계속 실행

```java
String expect = "Something";
String actual = "Something";
    
List<Integer> list1 = List.of(1, 2);
List<Integer> list2 = List.of(1, 2);
    
Assertions.assertAll("Assert All", List.of(
        () -> { Assertions.assertEquals(expect, actual); },
        () -> { Assertions.assertIterableEquals(list1, list2); }
));
```

- `assertTimeout()` 특정 작업이 지정 시간 내에 완료됨을 확인

Cf. 딱 맞게 시간을 설정하면  `1ms`, `2ms` 차이로 실패할 수 있다. 실패시 초과된 시간을 출력

```java
Assertions.assertTimeout(Duration.ofSeconds(3), () -> {
    Thread.sleep(2000);
});
```

- `assertTimeoutPreemptively()` 특정 작업이 지정 시간 내에 완료됨을 확인. 작업이 시간 초과되면 즉시 중단
Cf. 실패시 정해진 시간이 지나서 초과되었다고 출력

```java
Assertions.assertTimeoutPreemptively(Duration.ofSeconds(3), () -> {
    Thread.sleep(2000);
});
```

- `assertSame()` 두 객체가 동일한 객체임을 확인
- `assertNotSame()` 두 객체가 서로 다른 객체임을 확인
- `assertArrayEquals()` 두 배열이 동일함을 확인
- `assertDoesNotThrow()` 예외가 발생하지 않음을 확인
- `assertLinesMatch()` 두 문자열 리스트가 동일한 라인 순서와 패턴을 갖음을 확인
