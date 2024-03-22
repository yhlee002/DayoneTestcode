package com.jyujyu.dayonetest.service;

import com.jyujyu.dayonetest.model.StudentPass;
import com.jyujyu.dayonetest.repository.StudentFailRepository;
import com.jyujyu.dayonetest.repository.StudentPassRepository;
import com.jyujyu.dayonetest.repository.StudentScoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class StudentScoreServiceMockTest {

    @Test
    @DisplayName("첫번째 Mock 테스트")
    public void firstSaveScoreMockTest() {
        /* StudentScoreService는 내부에 세 개의 Repo를 가지고 있다.
           이들은 JPA가 인터페이스를 기반으로 만들어주는 것이며, 직접 구현하기에는 번거롭다.
           이를 위해 `Mockito.mock()`을 이용해 세 개의 인터페이스에 Mock 객체를 생성하고,
           이 세 개의 Mock 객체를 StudentScoreService 생성자에 인자로 전달해준다. (스프링이 해주듯)
         */
        // given
        StudentScoreService studentScoreService = new StudentScoreService(
            Mockito.mock(StudentScoreRepository.class),
            Mockito.mock(StudentPassRepository.class),
            Mockito.mock(StudentFailRepository.class)
        );

        // when
        String givenStudentName = "yhlee";
        String givenExam = "testexam";
        Integer givenKorScore = 80;
        Integer givenEngScore = 100;
        Integer givenMathScore = 60;

        studentScoreService.saveScore(
            givenStudentName,
            givenExam,
            givenKorScore,
            givenEngScore,
            givenMathScore
        );
    }
}
