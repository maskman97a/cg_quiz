package com.maskman97a.cg_quiz.service;

import com.maskman97a.cg_quiz.dto.AnswerDTO;
import com.maskman97a.cg_quiz.dto.QuestionCategoryDTO;
import com.maskman97a.cg_quiz.dto.QuestionDTO;
import com.maskman97a.cg_quiz.entity.AnswerEntity;
import com.maskman97a.cg_quiz.entity.QuestionCategoryEntity;
import com.maskman97a.cg_quiz.entity.QuestionEntity;
import com.maskman97a.cg_quiz.repository.AnswerRepository;
import com.maskman97a.cg_quiz.repository.QuestionCategoryRepository;
import com.maskman97a.cg_quiz.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private QuestionCategoryRepository questionCategoryRepository;

    // Thêm mới câu hỏi
    public void createQuestion(QuestionDTO questionDTO) {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setTitle(questionDTO.getTitle());
        questionEntity.setType(questionDTO.getType());
        questionEntity.setDifficult(questionDTO.getDifficulty());
        questionEntity.setQuestionCategoryId(questionDTO.getCategoryId());

        // Lưu câu hỏi
        questionEntity = questionRepository.save(questionEntity);

        // Thêm các câu trả lời
        for (AnswerDTO answerDTO : questionDTO.getAnswers()) {
            AnswerEntity answerEntity = new AnswerEntity();
            answerEntity.setValue(answerDTO.getValue());
            answerEntity.setCorrect(answerDTO.isCorrect());
            answerEntity.setDescription(answerDTO.getDescription());
            answerEntity.setQuestionId(questionEntity.getId());
            answerRepository.save(answerEntity);
        }
    }

    // Lấy danh sách câu hỏi với phân trang
    public Page<QuestionEntity> getQuestions(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    // Tìm kiếm câu hỏi theo tên
    public List<QuestionEntity> searchQuestions(String title) {
        return questionRepository.findByTitleContaining(title);
    }

    // Cập nhật câu hỏi
    public void updateQuestion(Long id, QuestionDTO questionDTO) {
        QuestionEntity questionEntity = questionRepository.findById(id).orElseThrow(() -> new RuntimeException("Question not found"));
        questionEntity.setTitle(questionDTO.getTitle());
        questionEntity.setType(questionDTO.getType());
        questionEntity.setDifficult(questionDTO.getDifficulty());
        questionEntity.setQuestionCategoryId(questionDTO.getCategoryId());
        questionRepository.save(questionEntity);
    }

    // Xóa câu hỏi
    public void deleteQuestion(Long id) {
        QuestionEntity questionEntity = questionRepository.findById(id).orElseThrow(() -> new RuntimeException("Question not found"));
        questionRepository.delete(questionEntity);
    }

    // Xem chi tiết câu hỏi
    public QuestionDTO getQuestionById(Long id) {
        QuestionEntity questionEntity = questionRepository.findById(id).orElseThrow(() -> new RuntimeException("Question not found"));
        List<AnswerEntity> answerEntities = answerRepository.findByQuestionIdOrderByValue(id);
        QuestionDTO questionDTO = modelMapper.map(questionEntity, QuestionDTO.class);
        questionDTO.setAnswers(answerEntities.stream().map(answerEntity -> modelMapper.map(answerEntity, AnswerDTO.class)).collect(Collectors.toList()));
        Optional<QuestionCategoryEntity> optionalQuestionCategoryEntity = questionCategoryRepository.findById(questionEntity.getId());
        optionalQuestionCategoryEntity.ifPresent(questionCategoryEntity -> questionDTO.setCategory(modelMapper.map(questionCategoryEntity, QuestionCategoryDTO.class)));
        return questionDTO;
    }


}
