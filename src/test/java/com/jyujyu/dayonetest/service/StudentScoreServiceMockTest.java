package com.jyujyu.dayonetest.service;

import com.jyujyu.dayonetest.model.StudentFail;
import com.jyujyu.dayonetest.model.StudentPass;
import com.jyujyu.dayonetest.repository.StudentFailRepository;
import com.jyujyu.dayonetest.repository.StudentPassRepository;
import com.jyujyu.dayonetest.repository.StudentScoreRepository;
import com.jyujyu.dayonetest.response.ExamFailStudentResponse;
import com.jyujyu.dayonetest.response.ExamPassStudentResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

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

    @Test
    @DisplayName("성적 저장 로직 검증 / 60점 이상인 경우 ")
    public void saveScoreMockTest() {
        // given
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentScoreService studentScoreService = new StudentScoreService(
            studentScoreRepository,
            studentPassRepository,
            studentFailRepository
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

        // then
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(studentPassRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(studentFailRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    @DisplayName("성적 저장 로직 검증 / 60점 이상인 경우 ")
    public void saveScoreMockTest2() {
        // given
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentScoreService studentScoreService = new StudentScoreService(
            studentScoreRepository,
            studentPassRepository,
            studentFailRepository
        );

        // when
        String givenStudentName = "yhlee";
        String givenExam = "testexam";
        Integer givenKorScore = 40;
        Integer givenEngScore = 40;
        Integer givenMathScore = 60;

        studentScoreService.saveScore(
            givenStudentName,
            givenExam,
            givenKorScore,
            givenEngScore,
            givenMathScore
        );

        // then
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(studentPassRepository, Mockito.times(0)).save(Mockito.any());
        Mockito.verify(studentFailRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("합격자 명단 가져오기 검증")
    public void getPassStudentListTest() { // 테스트 메서드명: 메서드명 + 'Test'가 관례
        // given
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);
        String givenExam = "testexam";

        StudentPass expectStudent1 = StudentPass.builder().id(1L).studentName("jyujyu").exam(givenExam).avgScore(70.0).build();
        StudentPass expectStudent2 = StudentPass.builder().id(2L).studentName("test").exam(givenExam).avgScore(80.0).build();
        StudentPass notExpectStudent3 = StudentPass.builder().id(3L).studentName("imnot").exam("secondexam").avgScore(90.0).build();

        /*
            Mockito.when()가 실행되면 thenReturn()을 리턴하도록 리턴값을 고정하는 역할 수행
         */
        Mockito.when(studentPassRepository.findAll()).thenReturn(List.of(
            expectStudent1,
            expectStudent2,
            notExpectStudent3
        ));

        StudentScoreService studentScoreService = new StudentScoreService(
            studentScoreRepository,
            studentPassRepository,
            studentFailRepository
        );

        // when
        List<ExamPassStudentResponse> responses = studentScoreService.getPassStudentList(givenExam);
        List<ExamPassStudentResponse> expectResponses = List.of(expectStudent1, expectStudent2)
            .stream().map(pass -> new ExamPassStudentResponse(
                pass.getStudentName(),
                pass.getAvgScore()
            )).toList();

        // then
        Assertions.assertIterableEquals(expectResponses, responses);
    }

    @Test
    @DisplayName("불합격자 명단 가져오기 검증")
    public void getFailStudentListTest() {
        // given
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);
        String givenExam = "testexam";

        StudentFail expectStudent1 = StudentFail.builder().id(2L).studentName("test").exam(givenExam).avgScore(50.0).build();
        StudentFail expectStudent2 = StudentFail.builder().id(3L).studentName("imnot").exam(givenExam).avgScore(45.0).build();
        StudentFail notExpectStudent3 = StudentFail.builder().id(1L).studentName("jyujyu").exam("secondexam").avgScore(35.0).build();

        Mockito.when(studentFailRepository.findAll()).thenReturn(List.of(
            expectStudent1,
            expectStudent2,
            notExpectStudent3
        ));

        StudentScoreService studentScoreService = new StudentScoreService(
            studentScoreRepository,
            studentPassRepository,
            studentFailRepository
        );

        // when
        List<ExamFailStudentResponse> responses = studentScoreService.getFailStudentList(givenExam);
        List<ExamFailStudentResponse> expectResponses = List.of(expectStudent1, expectStudent2)
            .stream().map(pass -> new ExamFailStudentResponse(
                pass.getStudentName(),
                pass.getAvgScore()
            )).toList();

        // then
        Assertions.assertIterableEquals(expectResponses, responses);
    }
}
