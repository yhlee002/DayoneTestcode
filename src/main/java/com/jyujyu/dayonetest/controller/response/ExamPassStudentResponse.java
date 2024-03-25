package com.jyujyu.dayonetest.controller.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/*
   객체의 값이 같으면 같다(equals)고 여기게 하려면
   @Getter + @EqualsAndHashCode를 이용하거나,
   @Data(단독)을 이용할 수 있다.
*/
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class ExamPassStudentResponse {
  private final String studentName;
  private final Double avgScore;
}
