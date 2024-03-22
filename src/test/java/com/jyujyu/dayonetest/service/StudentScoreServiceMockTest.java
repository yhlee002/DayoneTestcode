package com.jyujyu.dayonetest.service;

import com.jyujyu.dayonetest.MyCalculator;
import com.jyujyu.dayonetest.model.StudentFail;
import com.jyujyu.dayonetest.model.StudentPass;
import com.jyujyu.dayonetest.model.StudentScore;
import com.jyujyu.dayonetest.model.StudentScoreTestDataBuilder;
import com.jyujyu.dayonetest.repository.StudentFailRepository;
import com.jyujyu.dayonetest.repository.StudentPassRepository;
import com.jyujyu.dayonetest.repository.StudentScoreRepository;
import com.jyujyu.dayonetest.response.ExamFailStudentResponse;
import com.jyujyu.dayonetest.response.ExamPassStudentResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

public class StudentScoreServiceMockTest {
    private StudentScoreService studentScoreService;
    private StudentScoreRepository studentScoreRepository;
    private StudentPassRepository studentPassRepository;
    private StudentFailRepository studentFailRepository;


    @BeforeEach
    public void beforeEach() {
        /* StudentScoreService는 내부에 세 개의 Repo를 가지고 있다.
           이들은 JPA가 인터페이스를 기반으로 만들어주는 것이며, 직접 구현하기에는 번거롭다.
           이를 위해 `Mockito.mock()`을 이용해 세 개의 인터페이스에 Mock 객체를 생성하고,
           이 세 개의 Mock 객체를 StudentScoreService 생성자에 인자로 전달해준다. (스프링이 해주듯)
         */
        studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        studentPassRepository = Mockito.mock(StudentPassRepository.class);
        studentFailRepository = Mockito.mock(StudentFailRepository.class);

        studentScoreService = new StudentScoreService(
            studentScoreRepository,
            studentPassRepository,
            studentFailRepository
        );
    }

    @Test
    @DisplayName("첫번째 Mock 테스트")
    public void firstSaveScoreMockTest() {

        // given
        String givenStudentName = "yhlee";
        String givenExam = "testexam";
        Integer givenKorScore = 80;
        Integer givenEngScore = 100;
        Integer givenMathScore = 60;

        // when
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
        String givenStudentName = "yhlee";
        String givenExam = "testexam";
        Integer givenKorScore = 80;
        Integer givenEngScore = 100;
        Integer givenMathScore = 60;

        // ArgumentCaptor<T>: 메서드 호출시 전달되는 인자를 검증하고 활용도를 높이기 위해 캡쳐한다.
        // 두 클래스들에 대한 ArgumentCaptor 인스턴스 생성
        ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
        ArgumentCaptor<StudentPass> studentPassArgumentCaptor = ArgumentCaptor.forClass(StudentPass.class);

        // saveScore시 전달한 인자가 잘 사용되는지 확인하기 위한 테스트에서 비교를 위한 인스턴스 생성
        StudentScore expectedStudentScore = StudentScoreTestDataBuilder.passed().build();
        StudentPass expectedStudentPass = StudentPass.builder()
                .studentName(expectedStudentScore.getStudentName())
                    .exam(expectedStudentScore.getExam())
                        .avgScore(new MyCalculator(0.0)
                            .add(expectedStudentScore.getKorScore().doubleValue())
                            .add(expectedStudentScore.getEnglishScore().doubleValue())
                            .add(expectedStudentScore.getMathScore().doubleValue())
                            .divide(3.0)
                            .getResult())
                            .build();

        // when
        studentScoreService.saveScore(
            expectedStudentScore.getStudentName(),
            expectedStudentScore.getExam(),
            expectedStudentScore.getKorScore(),
            expectedStudentScore.getEnglishScore(),
            expectedStudentScore.getMathScore()
        );

        // then
        // ArgumentCaptor를 이용해 Repository에서 save가 일어날 때 인자를 캡쳐해 전달된 인자와 일치하는지 검증
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(studentScoreArgumentCaptor.capture());

        StudentScore capturedStudentScore = studentScoreArgumentCaptor.getValue();
        Assertions.assertEquals(expectedStudentScore.getStudentName(), capturedStudentScore.getStudentName());
        Assertions.assertEquals(expectedStudentScore.getExam(), capturedStudentScore.getExam());
        Assertions.assertEquals(expectedStudentScore.getKorScore(), capturedStudentScore.getKorScore());
        Assertions.assertEquals(expectedStudentScore.getEnglishScore(), capturedStudentScore.getEnglishScore());
        Assertions.assertEquals(expectedStudentScore.getMathScore(), capturedStudentScore.getMathScore());

        Mockito.verify(studentPassRepository, Mockito.times(1)).save(studentPassArgumentCaptor.capture());

        StudentPass capturedStudentPass = studentPassArgumentCaptor.getValue();
        Assertions.assertEquals(expectedStudentPass.getStudentName(), capturedStudentPass.getStudentName());
        Assertions.assertEquals(expectedStudentPass.getExam(), capturedStudentPass.getExam());
        Assertions.assertEquals(expectedStudentPass.getAvgScore(), capturedStudentPass.getAvgScore());

        Mockito.verify(studentFailRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    @DisplayName("성적 저장 로직 검증 / 60점 이상인 경우 ")
    public void saveScoreMockTest2() {
        // given
        StudentScore expectedStudentScore = StudentScoreTestDataBuilder.failed().build();
        StudentFail expectedStudentFail = StudentFail.builder()
            .studentName(expectedStudentScore.getStudentName())
            .exam(expectedStudentScore.getExam())
            .avgScore(new MyCalculator(0.0)
                .add(expectedStudentScore.getKorScore().doubleValue())
                .add(expectedStudentScore.getEnglishScore().doubleValue())
                .add(expectedStudentScore.getMathScore().doubleValue())
                .divide(3.0)
                .getResult())
            .build();

        ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
        ArgumentCaptor<StudentFail> studentFailArgumentCaptor = ArgumentCaptor.forClass(StudentFail.class);

        // when
        studentScoreService.saveScore(
            expectedStudentScore.getStudentName(),
            expectedStudentScore.getExam(),
            expectedStudentScore.getKorScore(),
            expectedStudentScore.getEnglishScore(),
            expectedStudentScore.getMathScore()
        );

        // then
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(studentScoreArgumentCaptor.capture());

        StudentScore capturedStudentScore = studentScoreArgumentCaptor.getValue();
        Assertions.assertEquals(expectedStudentScore.getStudentName(), capturedStudentScore.getStudentName());
        Assertions.assertEquals(expectedStudentScore.getExam(), capturedStudentScore.getExam());
        Assertions.assertEquals(expectedStudentScore.getKorScore(), capturedStudentScore.getKorScore());
        Assertions.assertEquals(expectedStudentScore.getEnglishScore(), capturedStudentScore.getEnglishScore());
        Assertions.assertEquals(expectedStudentScore.getMathScore(), capturedStudentScore.getMathScore());

        Mockito.verify(studentPassRepository, Mockito.times(0)).save(Mockito.any());
        Mockito.verify(studentFailRepository, Mockito.times(1)).save(studentFailArgumentCaptor.capture());

        StudentFail capturedStudentFail = studentFailArgumentCaptor.getValue();
        Assertions.assertEquals(expectedStudentFail.getStudentName(), capturedStudentFail.getStudentName());
        Assertions.assertEquals(expectedStudentFail.getExam(), capturedStudentFail.getExam());
        Assertions.assertEquals(expectedStudentFail.getAvgScore(), capturedStudentFail.getAvgScore());
    }

    @Test
    @DisplayName("합격자 명단 가져오기 검증")
    public void getPassStudentListTest() { // 테스트 메서드명: 메서드명 + 'Test'가 관례
        // given
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
        String givenExam = "testexam";

        StudentFail expectStudent1 = StudentFail.builder().id(2L).studentName("test").exam(givenExam).avgScore(50.0).build();
        StudentFail expectStudent2 = StudentFail.builder().id(3L).studentName("imnot").exam(givenExam).avgScore(45.0).build();
        StudentFail notExpectStudent3 = StudentFail.builder().id(1L).studentName("jyujyu").exam("secondexam").avgScore(35.0).build();

        Mockito.when(studentFailRepository.findAll()).thenReturn(List.of(
            expectStudent1,
            expectStudent2,
            notExpectStudent3
        ));

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
