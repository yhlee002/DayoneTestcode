package com.jyujyu.dayonetest.controller;

import com.jyujyu.dayonetest.request.SaveExamScoreRequest;
import com.jyujyu.dayonetest.response.ExamFailStudentResponse;
import com.jyujyu.dayonetest.response.ExamPassStudentResponse;
import com.jyujyu.dayonetest.service.StudentScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ScoreApi {

    private final StudentScoreService studentScoreService;

    @PutMapping("/exam/{exam}/score")
    public void save(@PathVariable("exam") String exam, @RequestBody SaveExamScoreRequest request) {

        studentScoreService.saveScore(
            request.getStudentName(),
            exam,
            request.getKorScore(),
            request.getEnglishScore(),
            request.getMathScore());
    }

    @GetMapping("/exam/{exam}/pass")
    public List<ExamPassStudentResponse> pass(@PathVariable("exam") String exam) {

        return studentScoreService.getPassStudentList(exam);
    }

    @GetMapping("/exam/{exam}/fail")
    public List<ExamFailStudentResponse> fail(@PathVariable("exam") String exam) {

        return studentScoreService.getFailStudentList(exam);
    }
}
