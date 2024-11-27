package com.maskman97a.cg_quiz.service;

import com.maskman97a.cg_quiz.entity.QuestionEntity;
import com.maskman97a.cg_quiz.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    // Get questions
    public Page<QuestionEntity> getQuestions(int page, int size) {
        return questionRepository.findAll(PageRequest.of(page, size));
    }

    // Search
    public Page<QuestionEntity> searchQuestions(String keyword, String difficulty, int page) {
        if (difficulty != null && !difficulty.isEmpty()) {
            return questionRepository.findByTitleContainingAndDifficulty(keyword, difficulty, PageRequest.of(page, 5));
        }
        return questionRepository.findByTitleContaining(keyword, PageRequest.of(page, 5));
    }

    // Save
    public QuestionEntity saveQuestion(QuestionEntity question) {
        return questionRepository.save(question);
    }

    // Get question details by ID
    public QuestionEntity getQuestionDetails(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() -> new IllegalArgumentException("Question not found"));
    }

    // Delete a question by ID
    public void deleteQuestion(Long questionId) {
        questionRepository.deleteById(questionId);
    }
}
