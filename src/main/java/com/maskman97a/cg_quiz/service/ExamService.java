package com.maskman97a.cg_quiz.service;

import com.maskman97a.cg_quiz.common.Const;
import com.maskman97a.cg_quiz.dto.AnswerInfoDto;
import com.maskman97a.cg_quiz.dto.ExamResultDto;
import com.maskman97a.cg_quiz.dto.QuestionInfoDto;
import com.maskman97a.cg_quiz.dto.enums.DifficultEnum;
import com.maskman97a.cg_quiz.dto.enums.ExamTypeEnum;
import com.maskman97a.cg_quiz.dto.enums.QuestionDifficultEnum;
import com.maskman97a.cg_quiz.entity.AnswerEntity;
import com.maskman97a.cg_quiz.entity.ExamDetailEntity;
import com.maskman97a.cg_quiz.dto.ExamDTO;
import com.maskman97a.cg_quiz.dto.enums.TypeExamEnum;
import com.maskman97a.cg_quiz.entity.ExamEntity;
import com.maskman97a.cg_quiz.entity.ExamResultAnswerEntity;
import com.maskman97a.cg_quiz.entity.ExamResultEntity;
import com.maskman97a.cg_quiz.entity.ExamResultQuestionEntity;
import com.maskman97a.cg_quiz.entity.QuestionEntity;
import com.maskman97a.cg_quiz.repository.AnswerRepository;
import com.maskman97a.cg_quiz.repository.ExamDetailRepository;
import com.maskman97a.cg_quiz.repository.ExamRepository;
import com.maskman97a.cg_quiz.repository.ExamResultAnswerRepository;
import com.maskman97a.cg_quiz.repository.ExamResultQuestionRepository;
import com.maskman97a.cg_quiz.repository.ExamResultRepository;
import com.maskman97a.cg_quiz.repository.QuestionRepository;
import com.maskman97a.cg_quiz.utils.DataUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExamService {
    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;
    private final ExamDetailRepository examDetailRepository;
    private final Random random = new Random();
    private final ModelMapper modelMapper;
    private final AnswerRepository answerRepository;
    private final UserService userService;
    private final ExamResultRepository examResultRepository;
    private final ExamResultQuestionRepository examResultQuestionRepository;
    private final ExamResultAnswerRepository examResultAnswerRepository;

    public Long initExam(DifficultEnum difficult) {
        List<QuestionEntity> questionEntities = questionRepository.findAll();
        List<QuestionEntity> easyList = questionEntities.stream().filter(x -> x.getDifficult() == QuestionDifficultEnum.EASY).toList();

        List<QuestionEntity> normalList = questionEntities.stream().filter(x -> x.getDifficult() == QuestionDifficultEnum.NORMAL).toList();

        List<QuestionEntity> hardList = questionEntities.stream().filter(x -> x.getDifficult() == QuestionDifficultEnum.HARD).toList();

        ExamEntity examEntity = new ExamEntity();
        examEntity.setName("EXAM-" + UUID.randomUUID());
        examEntity.setGraduateMark(80D);
        examEntity.setTotalTime(20);
        examEntity.setTotalQuestion(10);
        examEntity.setMaxPerson(1);
        examEntity.setType(ExamTypeEnum.valueOf(difficult.name()));
        examEntity = examRepository.save(examEntity);

        List<ExamDetailEntity> examDetailEntities = new ArrayList<>();
        Integer index = 1;
        index = getExamDetailEntities(examDetailEntities, difficult.getTotalEasy(), easyList, examEntity, index);
        index = getExamDetailEntities(examDetailEntities, difficult.getTotalNormal(), normalList, examEntity, index);
        getExamDetailEntities(examDetailEntities, difficult.getTotalHard(), hardList, examEntity, index);
        examDetailRepository.saveAll(examDetailEntities);
        return examEntity.getId();
    }

    private Integer getExamDetailEntities(List<ExamDetailEntity> examDetailEntities, int totalQuest, List<QuestionEntity> questList, ExamEntity examEntity, Integer index) {
        Set<Integer> lastIndex = new HashSet<>();
        for (int i = 1; i <= totalQuest; i++) {
            int randomIndex = random.nextInt(questList.size());
            while (lastIndex.contains(randomIndex)) {
                randomIndex = random.nextInt(questList.size());
            }
            lastIndex.add(randomIndex);
            QuestionEntity randomQuest = questList.get(randomIndex);
            ExamDetailEntity examDetailEntity = new ExamDetailEntity();
            examDetailEntity.setExamId(examEntity.getId());
            examDetailEntity.setQuestionId(randomQuest.getId());
            examDetailEntity.setIndex(index++);
            examDetailEntities.add(examDetailEntity);
        }
        return index;
    }

    public QuestionInfoDto getQuestion(Long examId, Integer questionNo) {
        QuestionEntity question = questionRepository.findByExamIdAndQuestionNo(examId, questionNo);
        QuestionInfoDto questionInfoDto = modelMapper.map(question, QuestionInfoDto.class);
        List<AnswerEntity> answerList = answerRepository.findByQuestionIdOrderByValue(question.getId());
        List<AnswerInfoDto> answerInfoList = answerList.stream().map(x -> modelMapper.map(x, AnswerInfoDto.class)).toList();
        List<ExamResultAnswerEntity> answerSubmitted = examResultAnswerRepository.findByExamIdAndQuestionNo(examId, questionNo);
        for (AnswerInfoDto answer : answerInfoList) {
            if (answerSubmitted.stream().anyMatch(x -> x.getAnswerId().equals(answer.getId()))) {
                answer.setSelected(true);
            }
        }
        questionInfoDto.setAnswerList(answerInfoList);

        return questionInfoDto;
    }

    public String answer(Long examId, Integer questionNo, Set<Long> answerIdList) {
        Optional<ExamEntity> exam = examRepository.findById(examId);
        if (exam.isPresent()) {
            ExamEntity examEntity = exam.get();
            QuestionEntity question = questionRepository.findByExamIdAndQuestionNo(examId, questionNo);

            //Check xem câu trả lời submit có tồn tại trên hệ thống
            List<AnswerEntity> listAnswer = answerRepository.findByQuestionIdAndCorrect(question.getId(), true);

            //Kiểm tra xem bài thi này đã được bắt đầu chưa?
            Optional<ExamResultEntity> optionalExamResultEntity = examResultRepository.getByExamId(examEntity.getId());
            ExamResultEntity examResultEntity;
            if (optionalExamResultEntity.isPresent()) {
                examResultEntity = optionalExamResultEntity.get();

                if (examResultEntity.getSubmitTime() != null) {
                    return "SUBMITTED";
                }
                if (examResultEntity.getExpiredDate().isBefore(LocalDateTime.now())) {
                    return "TIMEOUT";
                }
                //Kiểm tra xem câu hỏi đó đã được trả lời trước đó chưa
                Optional<ExamResultQuestionEntity> optionalExamResultQuestionEntity = examResultQuestionRepository.getByExamResultIdAndQuestionId(examResultEntity.getId(), question.getId());
                ExamResultQuestionEntity examResultQuestionEntity;
                if (optionalExamResultQuestionEntity.isEmpty()) {
                    examResultQuestionEntity = new ExamResultQuestionEntity();
                    examResultQuestionEntity.setExamResultId(examResultEntity.getId());
                    examResultQuestionEntity.setQuestionId(question.getId());
                    examResultQuestionEntity = examResultQuestionRepository.save(examResultQuestionEntity);
                } else {
                    examResultQuestionEntity = optionalExamResultQuestionEntity.get();
                }
                //Lấy danh sách các câu trả lời đã submit
                List<ExamResultAnswerEntity> answerList = examResultAnswerRepository.findByExamResultQuestionId(examResultQuestionEntity.getId());

                //Kiểm tra các câu trả lời đã bị hủy lựa chọn
                List<ExamResultAnswerEntity> removedAnswer = answerList.stream().filter(x -> !answerIdList.contains(x.getAnswerId())).toList();
                examResultAnswerRepository.deleteAll(removedAnswer);

                //Kiểm tra xem câu trả lời đã được ghi nhận hay chưa
                for (Long answerId : answerIdList) {
                    if (!answerList.stream().map(ExamResultAnswerEntity::getAnswerId).toList().contains(answerId)) {
                        ExamResultAnswerEntity examResultAnswerEntity = new ExamResultAnswerEntity();
                        examResultAnswerEntity.setExamResultQuestionId(examResultQuestionEntity.getId());
                        examResultAnswerEntity.setAnswerId(answerId);
                        examResultAnswerRepository.save(examResultAnswerEntity);
                    }
                }
            }
        }
        return "answered";
    }

    public void submit(Long examId) {
        Optional<ExamEntity> optionalExamEntity = examRepository.findById(examId);
        if (optionalExamEntity.isPresent()) {
            Optional<ExamResultEntity> optionalExamResultEntity = examResultRepository.getByExamId(examId);
            if (optionalExamResultEntity.isPresent()) {
                int mark = 0;
                if (optionalExamResultEntity.get().getSubmitTime() == null) {
                    optionalExamResultEntity.get().setSubmitTime(LocalDateTime.now());
                }


                List<QuestionEntity> questionEntities = questionRepository.findByExamId(examId);

                List<ExamResultQuestionEntity> questionResultList = examResultQuestionRepository.findByExamResultId(optionalExamResultEntity.get().getId());

                List<AnswerEntity> answerEntities = answerRepository.findAllRightAnswerForQuestion(questionEntities.stream().map(QuestionEntity::getId).toList());

                List<ExamResultAnswerEntity> answerResultList = examResultAnswerRepository.findByExamResultId(optionalExamResultEntity.get().getId());

                for (QuestionEntity question : questionEntities) {
                    //Tim kiem cau hoi da duoc tra loi
                    Optional<ExamResultQuestionEntity> optionalExamResultQuestionEntity = questionResultList.stream().filter(questionResult -> questionResult.getQuestionId().equals(question.getId())).findFirst();
                    if (optionalExamResultQuestionEntity.isPresent()) {
                        //Lấy danh sách đáp án
                        List<AnswerEntity> answerListOfQuestion = answerEntities.stream().filter(answer -> answer.getQuestionId().equals(question.getId())).toList();
                        boolean isRight = true;
                        for (AnswerEntity answerEntity : answerListOfQuestion) {
                            if (answerResultList.stream().noneMatch(x -> x.getAnswerId().equals(answerEntity.getId()))) {
                                isRight = false;
                            }
                        }
                        if (isRight) {
                            mark += 10;
                        }
                    }
                }
                optionalExamResultEntity.get().setMark(mark);
                examResultRepository.save(optionalExamResultEntity.get());
            }
        }
    }

    public ExamResultEntity result(Long examId) {
        Optional<ExamEntity> optionalExamEntity = examRepository.findById(examId);
        if (optionalExamEntity.isPresent()) {
            Optional<ExamResultEntity> optionalExamResultEntity = examResultRepository.getByExamId(examId);
            if (optionalExamResultEntity.isPresent()) {
                return optionalExamResultEntity.get();
            }
        }
        return null;
    }

    public String start(Long examId) {
        Optional<ExamResultEntity> optionalExamResultEntity = examResultRepository.getByExamId(examId);
        ExamResultEntity examResultEntity = new ExamResultEntity();
        if (optionalExamResultEntity.isEmpty()) {
            examResultEntity.setExamId(examId);
            examResultEntity.setMark(0);
            examResultEntity.setExpiredDate(LocalDateTime.now().plusMinutes(20));
            examResultEntity.setUserId(userService.getCurrentUser() != null ? userService.getCurrentUser().getId() : null);
            examResultRepository.save(examResultEntity);
        } else {
            examResultEntity = optionalExamResultEntity.get();
        }
        if (examResultEntity.getExpiredDate().isBefore(LocalDateTime.now())) {
            return null;
        }
        return examResultEntity.getExpiredDate().toString();
    }

    public List<ExamResultDto> examHistory(Long userId) {
        return DataUtils.convertList(examResultRepository.findAllByUserId(13L), x -> modelMapper.map(x, ExamResultDto.class));
    }

    public Page<ExamResultDto>  findExamResultsByUserId(Long userId, Pageable pageable) {
        Page<ExamResultEntity> examHistories = examResultRepository.findByUserId(userId, pageable);
        return examHistories.map(examHistory -> modelMapper.map(examHistory, ExamResultDto.class));
    }

    public List<ExamEntity> getList(){
        return examRepository.findAll();
    }


    public void create(ExamEntity exam) {
        examRepository.save(exam);
    }

    public void updateExam(ExamEntity exam) {
        examRepository.save(exam);
    }
}
