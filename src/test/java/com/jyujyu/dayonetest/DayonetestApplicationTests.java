package com.jyujyu.dayonetest;

import com.jyujyu.dayonetest.model.StudentScore;
import com.jyujyu.dayonetest.model.StudentScoreFixture;
import com.jyujyu.dayonetest.repository.StudentScoreRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

// @SpringBootTest는 부모 클래스에서 정의되어있어 생략
class DayonetestApplicationTests extends IntegrationTest {

  @Autowired private StudentScoreRepository studentScoreRepository;

  @Autowired private EntityManager entityManager;

  @Test
  void contextLoads() {
    StudentScore studentScore = StudentScoreFixture.passed();
    StudentScore savedStudentScore = studentScoreRepository.save(studentScore);

    entityManager.flush();
    entityManager.clear();

    StudentScore queryStudentScore =
        studentScoreRepository.findById(savedStudentScore.getId()).orElseThrow();

    Assertions.assertEquals(savedStudentScore.getId(), queryStudentScore.getId());
    Assertions.assertEquals(savedStudentScore.getStudentName(), queryStudentScore.getStudentName());
    Assertions.assertEquals(savedStudentScore.getKorScore(), queryStudentScore.getKorScore());
    Assertions.assertEquals(
        savedStudentScore.getEnglishScore(), queryStudentScore.getEnglishScore());
    Assertions.assertEquals(savedStudentScore.getMathScore(), queryStudentScore.getMathScore());
  }
}
