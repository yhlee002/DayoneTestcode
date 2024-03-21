# JUnit : 테스트의 시작
## 1. Test Code 구조
   ### 1. AAA Pattern
      - Arrange : 준비
      - Act : 실행
      - Assert : 단언/검증
   ### 2. GWT Pattern
      - Given (= Arange step in AAA) : 준비
      - When (= Act step in AAA) : 행동/연산
      - Then (= Assert step in AAA) : 검증
## 2. Assertions
   - `Assertions.assertEquals(T expected, T actual)` : 값 검증
   - `Assertions.assertThrows(T expected, Executable e)` : Exception 검증
```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
class Test {
    
    @Test
    void sum() {
       // given
       Double val = 5.0;
       
       // when
       val += 5.0;
       
       // then
       Assertions.assertEquals(10.0, val);
    }
    
    @Test
    void divideZero() {
        // given
        Double num = 0.0;

        // when & then
        Assertions.assertThrows(RuntimeException.class, () -> {
           Double val = 1.0;
           val /= num;
        });
    }
}
   ```

