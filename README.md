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

## 3. DisplayNameGeneration, DisplayName

### DisplayNameGeneration

만약, 두 어노테이션이 모두 사용되었다면 `DisplayNameGeneration` 보다 `DisplayName` 이 우선된다.

클래스에 붙이는 어노테이션이다. `DisplayNameGenerator`의 하위 클래스를 인자로 받아, 일괄적으로 테스트명들을 수정해 보여준다.
```java
@DisplayNameGeneration(DisplayGenerator.ReplaceUnderscores.class)
class JUnitTest {
    
    @Test
    public void assert_Equals_Test() {
        ...
    }
}
```
 위와 같이 `DisplayNameGenerator.ReplaceUnderscores` 규칙을 이용하면 아래와 같이 언더스코어(`_`)를 띄어쓰기로 대치하여 보여준다.

![img.png](src/main/resources/static/img/img.png)

### DisplayName
 테스트 메서드에 붙이는 어노테이션으로, 개별 테스트의 테스트명을 원하는 텍스트로 보이게끔 하기 위해 사용한다.
```java
    @Test
    @DisplayName("assertEquals 테스트")
    public void assertEqualsTest() {
        ...
    }
```
 위와 같이 작성시 아래와 같이 명시한 텍스트로 테스트명이 출력되는 것을 볼 수 있다.

![img.png](src/main/resources/static/img/img2.png)

## 4. RepeatedTest, ParameterizedTest
 테스트를 반복 실행하는데 사용되는 어노테이션이다.

### RepeatedTest
 테스트를 반복 실행하기 위해 사용된다. 단순 반복이기 때문에 안정성 및 견고성을 테스트하기 위해 사용된다.
 
아래 코드 실행시 동일한 테스트가 5회 단순 반복된다.
```java
@DisplayName("덧셈을 5회 단순 반복 테스트")
@RepeatedTest(5)
public void repeatedAddTest() {
    // given
    MyCalculator cal = new MyCalculator();
    
    // when
    cal.add(10.0);
    
    // then
    Assertions.assertEquals(10.0, cal.getResult());
}
```

### ParameterizedTest
 매개변수 집합을 이용해 테스트를 실행시킬 때 사용된다.
 일반적으로 샘플 데이터를 제공하는 `@ValueSource`, `@EnumSource`, `@MethodSource`, `@CsvSource`, `@CsvFileSource` 등과 함께 사용된다.

 아래는 주로 많이 사용되는 `@MethodSource`를 이용해 매개변수 집합을 전달받는 테스트이다. 
 `@MethodSource` 사용시 인자 집합을 전달해주는 메서드명을 인자로 전달해야하며, 인자 집합을 전달해주는 메서드는 `Stream<Arguments`> 타입으로 결과를 반환해야 한다.
```java
@DisplayName("매개변수를 이용한 6회 반복 테스트")
@ParameterizedTest @MethodSource("parameterizedTestParameters")
public void parameterizedTest(Double addVal, Double expectVal) {
    // given
    MyCalculator cal = new MyCalculator(0.0);

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
```

> Cf. 이 때, `Arguments`는 `org.junit.jupiter.params.provider` 패키지에 속한 클래스이다.

## 3. Mockito

> Mock이란? 모의 객체 즉, 가짜 객체를 의미한다. 실제 객체와 달리 개발자가 직접 객체의 행동을 관리할 수 있다.

 Mockito는 Mocking Framework이다. Mock 객체를 쉽게 만들고 관리하고 검증할 수 있게 해준다.

### 간단한 사용법

#### Mockito 의존성 추가

```groovy
dependencies {
    testImplementation "org.mockito:mockito-core:3.+"
}
```

#### 1) mock()
  mock() 을 통해 특정 인터페이스나 클래스를 구현하는 객체의 목(Mock)을 생성할 수 있다. 
 생성된 목 객체는 기본적으로 null, 0, false 등 기본값을 반환하나`when().thenReturn()`를 
  사용하여 원하는 값을 반환하도록 동작을 설정할 수 있다. 이를 통해 테스트할 클래스가 의존하는 객체들을
  실제로 구현하지 않고도 가짜로 만들어 테스트를 진행할 수 있다.

```java
class ExampleClass {
    public interface CustomObject {
        String sayHi();
    }
    public static void main(String[] args) {
        CustomObject mockObj = Mockito.mock(CustomObject.class);
        Mockito.when(mockObj::hello).thenReturn("Hi, This is mock!");
    }
}
```

Cf. 클래스 내부에 필드 변수로 객체를 주입받는 경우

이를 Mock 객체로 대체하고자 하는 경우 `@Mock`을 사용할 수 있다.
```java
class ExampleObject {
    @Mock
    private ExmpleObject2 exmpleObject2;
}
```

Cf. 빈(Bean)을 Mocking하는 경우

실제 빈을 주입받는 경우 `@Autowired`를 사용하거나 
클래스 자체에 `@RequiredArgsConstructor`을 추가하고 `final` 변수로 필드를 구성하기도 하지만,
빈으로 등록하는 객체를 Mocking하는 경우 `@MockBean`을 사용할 수 있다.
```java
class ExampleObject {
    @MockBean 
    private CustomService customService;
}
```

#### 2) spy()

`spy()`는 `mock()`와는 다르게 실제 객체를 래핑(wrapping)하여 사용하기 때문에 
필요한 경우 일부 메서드의 동작을 원하는대로 조작할 수 있다.

```java
import org.mockito.Mockito;

class ExampleClass {
    public static class CustomObject {
        String sayHi() {
            return "Hi, This is a real object!";
        }
        
        String sayHi2() {
            return "Hi, This is a real object!";
        }
    }
    public static void main(String[] args) {
        CustomObject realObj = new CustomObject();
        CustomObject mockObj = Mockito.spy(realObj);
        Mockito.when(mockObj::sayHi).thenReturn("Hi, This is a mock!");
        
        mockObj.sayHi(); // Hi, This is a mock!
        mockObj.sayHi2(); // "Hi, This is a real object!"
    }
}
```

### 검증하기
#### 1) verify()

`verify()`를 사용하면 테스트 중에 목(Mock) 객체와의 상호작용을 검증할 수 있다. 메소드가 예상대로 호출되었는지, 호출 횟수가 맞는지, 호출 순서가 맞는지 등을 확인할 수 있다.

#### 2) times()

`times()`는 특정 목 객체의 메소드 호출 횟수를 검증하는데 사용된다. 테스트 중에 모의 객체와 상호작용이 예상대로 이루어지는지를 확인할 수 있다.

Cf. `times()`를 사용하여 메소드 호출 횟수를 정확하게 검증할 수 있다.

#### 3) ArgumentCaptor

`ArgumentCaptor`는 목 객체를 사용해 메소드 호출 시 전달된 인자들을 캡처하고 추출하는 기능을 제공하는 클래스입니다. 특정 메소드가 호출될 때 전달된 인자들을 테스트 중에 검증하거나, 다른 곳에서 사용할 수 있도록 할 때 유용하게 사용됩니다.

```java
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExampleClass {
    public static class CustomObject {
        String sayValue(String value) {
            return value;
        }
    }
    
    @Test
    public void test1() {
        CustomObject mockObj = Mockito.mock(CustomObject.class);
        mockObj.sayValue("Hi");
        
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        // 한 번 실행됨을 검증하는 경우
        Mockito.verify(mockObj, Mockito.timeout(1000).times(1)).sayValue(captor.capture());

        // 한 번도 실행되지 않음을 검증하는 경우
        Mockito.verify(mockObj, Mockito.timeout(1000).times(0)).sayValue(captor.capture());

        // 캡쳐된 값 검증
        Assertions.assertEquals("Hi", captor.getValue());
    }
}
```
