package com.jyujyu.dayonetest.service;

import com.jyujyu.dayonetest.IntegrationTest;
import com.jyujyu.dayonetest.MyCalculator;
import com.jyujyu.dayonetest.model.StudentScore;
import com.jyujyu.dayonetest.model.StudentScoreFixture;
import com.jyujyu.dayonetest.response.ExamFailStudentResponse;
import com.jyujyu.dayonetest.response.ExamPassStudentResponse;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StudentScoreServiceIntegrationTest extends IntegrationTest {

    @Autowired
    private StudentScoreService studentScoreService;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void savePassedStudentTest() {
        // given
        StudentScore studentScore = StudentScoreFixture.passed();

        // when
        studentScoreService.saveScore(
            studentScore.getStudentName(),
            studentScore.getExam(),
            studentScore.getKorScore(),
            studentScore.getEnglishScore(),
            studentScore.getMathScore()
        );

        entityManager.flush();
        entityManager.clear();

        // then
        List<ExamPassStudentResponse> passedStudentResponses = studentScoreService.getPassStudentList(studentScore.getExam());

        Assertions.assertEquals(1, passedStudentResponses.size());

        ExamPassStudentResponse passedStudentReponse = passedStudentResponses.get(0);

        MyCalculator cal = new MyCalculator(0.0);
        Assertions.assertEquals(studentScore.getStudentName(), passedStudentReponse.getStudentName());
        Assertions.assertEquals(
            cal.add(studentScore.getKorScore().doubleValue())
                .add(studentScore.getEnglishScore().doubleValue())
                .add(studentScore.getMathScore().doubleValue())
                .divide(3.0)
                .getResult()
            , passedStudentReponse.getAvgScore());
    }

    @Test
    public void saveFailedStudentTest() {
        // given
        StudentScore studentScore = StudentScoreFixture.failed();

        // when
        studentScoreService.saveScore(
            studentScore.getStudentName(),
            studentScore.getExam(),
            studentScore.getKorScore(),
            studentScore.getEnglishScore(),
            studentScore.getMathScore()
        );

        entityManager.flush();
        entityManager.clear();

        // then
        List<ExamFailStudentResponse> failedStudentResponses = studentScoreService.getFailStudentList(studentScore.getExam());

        Assertions.assertEquals(1, failedStudentResponses.size());

        ExamFailStudentResponse failedStudentReponse = failedStudentResponses.get(0);

        MyCalculator cal = new MyCalculator(0.0);
        Assertions.assertEquals(studentScore.getStudentName(), failedStudentReponse.getStudentName());
        Assertions.assertEquals(
            cal.add(studentScore.getKorScore().doubleValue())
                .add(studentScore.getEnglishScore().doubleValue())
                .add(studentScore.getMathScore().doubleValue())
                .divide(3.0)
                .getResult()
            , failedStudentReponse.getAvgScore());
    }
}
