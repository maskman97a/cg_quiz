package com.maskman97a.cg_quiz.controller;

import com.maskman97a.cg_quiz.dto.ExamResultDto;
import com.maskman97a.cg_quiz.dto.QuestionInfoDto;
import com.maskman97a.cg_quiz.dto.enums.DifficultEnum;
import com.maskman97a.cg_quiz.dto.enums.GraduateEnum;
import com.maskman97a.cg_quiz.dto.request.SubmitAnswerReq;
import com.maskman97a.cg_quiz.dto.response.TimeRemainResponse;
import com.maskman97a.cg_quiz.entity.ExamResultEntity;
import com.maskman97a.cg_quiz.entity.QuestionEntity;
import com.maskman97a.cg_quiz.service.ExamService;
import com.maskman97a.cg_quiz.utils.DataUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/exam")
@RequiredArgsConstructor
public class ExamController extends BaseController {
    private final ExamService examService;
    @Autowired
    public ModelMapper modelMapper;

    @GetMapping
    public String getExamStartPage(HttpServletRequest request, Model model) {
        model.addAttribute("userId", 13L);
        return renderPage(request, model, "exam", "main");
    }

    @GetMapping("/init")
    public String initExam(HttpServletRequest request, Model model, @RequestParam("level") DifficultEnum difficult) {
        Long examId = examService.initExam(difficult);
        model.addAttribute("examId", examId);
        model.addAttribute("totalQuestion", "10 câu");
        model.addAttribute("totalTime", "20 phút");
        model.addAttribute("difficult", difficult.getDescription());
        return renderPage(request, model, "exam", "init");
    }

    @GetMapping("/start")
    public String start(HttpServletRequest req, Model model, @RequestParam("examId") Long examId) {
        model.addAttribute("examId", examId);
        String expiredTime = examService.start(examId);
        if (expiredTime == null) {
            model.addAttribute("startExamMsg", "Bài thi đã hết giờ");
        }
        model.addAttribute("expiredTime", expiredTime);
        return renderPage(req, model, "exam", "doing");
    }

    @GetMapping("/get-question")
    public ResponseEntity<QuestionInfoDto> getQuestion(@RequestParam("examId") Long examId, @RequestParam("questionNo") Integer questionNo) {
        return ResponseEntity.ok(examService.getQuestion(examId, questionNo));
    }

    @PostMapping("/answer")
    public ResponseEntity<String> doAnswer(@RequestBody SubmitAnswerReq req) {
        return ResponseEntity.ok(examService.answer(req.getExamId(), req.getQuestionNo(), req.getAnswerIdList()));
    }

    @GetMapping("/submit")
    public String doSubmit(HttpServletRequest request, Model model, @RequestParam("examId") Long examId) {
        examService.submit(examId);
        return result(request, model, examId);
    }

    @GetMapping("/result")
    public String result(HttpServletRequest request, Model model, @RequestParam("examId") Long examId) {
        ExamResultEntity examResultEntity = examService.result(examId);
        if (examResultEntity != null) {
            model.addAttribute("pass", examResultEntity.getMark() > 80);
            model.addAttribute("graduate", examResultEntity.getMark() > 80 ? "Đạt yêu cầu" : "Không đạt");
            model.addAttribute("mark", examResultEntity.getMark());
            model.addAttribute("endTime", examResultEntity.getSubmitTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            model.addAttribute("startTime", examResultEntity.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            return renderPage(request, model, "exam", "result");
        }
        return getExamStartPage(request, model);
    }

    @GetMapping("/history")
    public String examHistory(HttpServletRequest request,
                              Model model,
                              @RequestParam(value = "userId", required = false) Long userId,
                              @RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "5") int size) {
        Page<ExamResultDto> examResults = examService.findExamResultsByUserId(13L, PageRequest.of(page, size));
        model.addAttribute("examHistories", examResults.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", examResults.getTotalPages());
        model.addAttribute("totalItems", examResults.getTotalElements());
        model.addAttribute("userId", 13L);
        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = 0; i < examResults.getTotalPages(); i++) {
            pageNumbers.add(i);
        }
        model.addAttribute("pageNumbers", pageNumbers);
        return renderPage(request, model, "exam", "history");
    }
}
