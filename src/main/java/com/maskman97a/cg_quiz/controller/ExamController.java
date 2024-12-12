package com.maskman97a.cg_quiz.controller;

import com.maskman97a.cg_quiz.dto.ExamDTO;
import com.maskman97a.cg_quiz.dto.ExamResultDto;
import com.maskman97a.cg_quiz.dto.QuestionInfoDto;
import com.maskman97a.cg_quiz.dto.enums.DifficultEnum;
import com.maskman97a.cg_quiz.dto.enums.GraduateEnum;
import com.maskman97a.cg_quiz.dto.request.SubmitAnswerReq;
import com.maskman97a.cg_quiz.dto.response.ExamResultDetailDto;
import com.maskman97a.cg_quiz.dto.response.TimeRemainResponse;
import com.maskman97a.cg_quiz.entity.ExamEntity;
import com.maskman97a.cg_quiz.entity.ExamResultEntity;
import com.maskman97a.cg_quiz.entity.QuestionEntity;
import com.maskman97a.cg_quiz.service.ExamService;
import com.maskman97a.cg_quiz.service.UserService;
import com.maskman97a.cg_quiz.utils.DataUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    private final UserService userService;

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
                              @RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "5") int size) {
        Page<ExamResultDto> examResults = examService.findExamResultsByUserId(PageRequest.of(page, size));
        model.addAttribute("examHistories", examResults.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", examResults.getTotalPages());
        model.addAttribute("totalItems", examResults.getTotalElements());
        model.addAttribute("userId", userService.getCurrentUser().getId());
        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = 0; i < examResults.getTotalPages(); i++) {
            pageNumbers.add(i);
        }
        model.addAttribute("pageNumbers", pageNumbers);
        return renderPage(request, model, "exam", "history");
    }

    @GetMapping("/history-detail")
    public String examHistoryDetail(HttpServletRequest request,
                                    Model model,
                                    @RequestParam(value = "result", required = false) Long result,
                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "5") int size) {
        Page<ExamResultDetailDto> examDetails = examService.findExamResultsDetail(PageRequest.of(page, size),result);
        model.addAttribute("examDetails", examDetails.getContent());
        model.addAttribute("currentPageDetail", page);
        model.addAttribute("totalPagesDetail", examDetails.getTotalPages());
        model.addAttribute("totalItemsDetail", examDetails.getTotalElements());
        model.addAttribute("examResultId", result);
        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = 0; i < examDetails.getTotalPages(); i++) {
            pageNumbers.add(i);
        }
        model.addAttribute("pageNumbersDetail", pageNumbers);
        return renderPage(request, model, "exam", "history-detail");
    }

    @GetMapping("/list")
    public String getExamListPage(HttpServletRequest httpServletRequest, Model model) {
        List<ExamEntity> examList = examService.getList();
        model.addAttribute("examList", examList);
        return renderPage(httpServletRequest, model, "exam", "list");
    }

    @GetMapping("/create")
    public String create(HttpServletRequest httpServletRequest, Model model) {
        model.addAttribute("exam", new ExamEntity());
        return renderPage(httpServletRequest, model, "exam", "create");
    }

    @PostMapping("/create")
    public String save(@Valid @ModelAttribute("exam") ExamDTO examDTO, BindingResult bindingResult, HttpServletRequest httpServletRequest, Model model) {
        new ExamDTO().validate(examDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return renderPage(httpServletRequest, model, "exam", "create");
        }
        ExamEntity exam = new ExamEntity();
        BeanUtils.copyProperties(examDTO, exam);
        examService.create(exam);
        return "redirect:/exam/list";
    }
}
