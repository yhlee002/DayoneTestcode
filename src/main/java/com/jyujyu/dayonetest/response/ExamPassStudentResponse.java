package com.jyujyu.dayonetest.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExamPassStudentResponse {
    private final String studentName;
    private final Double avgScore;
}
