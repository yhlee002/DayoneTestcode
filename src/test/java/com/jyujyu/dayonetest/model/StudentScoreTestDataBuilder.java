package com.jyujyu.dayonetest.model;

// Test data Builder Pattern
public class StudentScoreTestDataBuilder {

  public static StudentScore.StudentScoreBuilder passed() {
    return StudentScore.builder()
        .korScore(80)
        .englishScore(100)
        .mathScore(90)
        .studentName("defaultName") // studentName, exam: overriding 가능
        .exam("defaultExam");
  }

  public static StudentScore.StudentScoreBuilder failed() {
    return StudentScore.builder()
        .korScore(40)
        .englishScore(40)
        .mathScore(30)
        .studentName("defaultName")
        .exam("defaultExam");
  }
}
