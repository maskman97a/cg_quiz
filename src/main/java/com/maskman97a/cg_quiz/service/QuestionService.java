import com.maskman97a.cg_quiz.dto.AnswerDTO;
import com.maskman97a.cg_quiz.dto.QuestionDTO;
import com.maskman97a.cg_quiz.dto.enums.QuestionDifficultEnum;
import com.maskman97a.cg_quiz.dto.enums.QuestionTypeEnum;
import com.maskman97a.cg_quiz.entity.AnswerEntity;
import com.maskman97a.cg_quiz.entity.QuestionCategoryEntity;
import com.maskman97a.cg_quiz.entity.QuestionEntity;
import com.maskman97a.cg_quiz.repository.AnswerRepository;
import com.maskman97a.cg_quiz.repository.QuestionCategoryRepository;
import com.maskman97a.cg_quiz.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ModelMapper modelMapper;
    private final QuestionCategoryRepository questionCategoryRepository;

    /**
     * Tạo câu hỏi mới
     */
    @Transactional
    public void createQuestion(QuestionDTO questionDTO) {
        // Tạo entity câu hỏi
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setTitle(questionDTO.getTitle());
        questionEntity.setType(questionDTO.getType());
        questionEntity.setDifficult(questionDTO.getDifficulty());

        // Lấy danh mục câu hỏi từ DB
        QuestionCategoryEntity categoryEntity = questionCategoryRepository.findById(questionDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Danh mục không tồn tại"));
        questionEntity.setQuestionCategoryId(categoryEntity.getId());

        // Lưu câu hỏi vào DB
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

    /**
     * Lấy danh sách câu hỏi với phân trang
     */
    public Page<QuestionDTO> getQuestions(Pageable pageable) {
        return questionRepository.findAll(pageable)
                .map(questionEntity -> {
                    QuestionDTO questionDTO = modelMapper.map(questionEntity, QuestionDTO.class);

                    // Chuyển loại câu hỏi và độ khó sang tên hiển thị
                    questionDTO.setType(QuestionTypeEnum.valueOf(questionEntity.getType()).getDisplayName());
                    questionDTO.setDifficulty(QuestionDifficultEnum.valueOf(questionEntity.getDifficult()).getDisplayName());

                    // Lấy tên danh mục
                    Optional<QuestionCategoryEntity> optionalCategory = questionCategoryRepository.findById(questionEntity.getQuestionCategoryId());
                    optionalCategory.ifPresent(category -> questionDTO.setCategoryName(category.getName()));

                    return questionDTO;
                });
    }

    /**
     * Tìm kiếm câu hỏi theo tiêu đề và độ khó
     */
    public List<QuestionDTO> searchQuestions(String keyword, QuestionDifficultEnum difficult) {
        List<QuestionEntity> questions = questionRepository.findByTitleContaining(keyword, difficult);

        return questions.stream().map(questionEntity -> {
            QuestionDTO questionDTO = modelMapper.map(questionEntity, QuestionDTO.class);

            // Chuyển loại câu hỏi và độ khó sang tên hiển thị
            questionDTO.setType(QuestionTypeEnum.valueOf(questionEntity.getType()).getDisplayName());
            questionDTO.setDifficulty(QuestionDifficultEnum.valueOf(questionEntity.getDifficult()).getDisplayName());

            // Lấy tên danh mục
            Optional<QuestionCategoryEntity> optionalCategory = questionCategoryRepository.findById(questionEntity.getQuestionCategoryId());
            optionalCategory.ifPresent(category -> questionDTO.setCategoryName(category.getName()));

            return questionDTO;
        }).collect(Collectors.toList());
    }

    /**
     * Cập nhật câu hỏi
     */
    @Transactional
    public void updateQuestion(Long id, QuestionDTO questionDTO) {
        // Lấy entity câu hỏi từ DB
        QuestionEntity questionEntity = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Câu hỏi không tồn tại"));

        // Cập nhật thông tin câu hỏi
        questionEntity.setTitle(questionDTO.getTitle());
        questionEntity.setType(questionDTO.getType());
        questionEntity.setDifficult(questionDTO.getDifficulty());

        // Lấy danh mục từ DB và cập nhật
        QuestionCategoryEntity categoryEntity = questionCategoryRepository.findById(questionDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Danh mục không tồn tại"));
        questionEntity.setQuestionCategoryId(categoryEntity.getId());

        questionRepository.save(questionEntity);

        // Cập nhật câu trả lời
        for (AnswerDTO answerDTO : questionDTO.getAnswers()) {
            AnswerEntity answerEntity = answerRepository.findById(answerDTO.getId())
                    .orElseGet(() -> new AnswerEntity());
            answerEntity.setValue(answerDTO.getValue());
            answerEntity.setCorrect(answerDTO.isCorrect());
            answerEntity.setDescription(answerDTO.getDescription());
            answerEntity.setQuestionId(questionEntity.getId());
            answerRepository.save(answerEntity);
        }
    }

    /**
     * Xóa câu hỏi
     */
    @Transactional
    public void deleteQuestion(Long id) {
        QuestionEntity questionEntity = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Câu hỏi không tồn tại"));

        // Xóa các câu trả lời liên quan
        List<AnswerEntity> answers = answerRepository.findByQuestionId(questionEntity.getId());
        answerRepository.deleteAll(answers);

        // Xóa câu hỏi
        questionRepository.delete(questionEntity);
    }

    /**
     * Lấy thông tin chi tiết câu hỏi theo ID
     */
    public QuestionDTO getQuestionById(Long id) {
        QuestionEntity questionEntity = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Câu hỏi không tồn tại"));

        // Lấy danh sách câu trả lời
        List<AnswerEntity> answerEntities = answerRepository.findByQuestionId(questionEntity.getId());

        // Map sang DTO
        QuestionDTO questionDTO = modelMapper.map(questionEntity, QuestionDTO.class);
        questionDTO.setAnswers(answerEntities.stream()
                .map(answerEntity -> modelMapper.map(answerEntity, AnswerDTO.class))
                .collect(Collectors.toList()));

        // Lấy danh mục
        Optional<QuestionCategoryEntity> categoryEntity = questionCategoryRepository.findById(questionEntity.getQuestionCategoryId());
        categoryEntity.ifPresent(category -> questionDTO.setCategoryName(category.getName()));

        // Chuyển loại câu hỏi và độ khó sang tên hiển thị
        questionDTO.setType(QuestionTypeEnum.valueOf(questionEntity.getType()).getDisplayName());
        questionDTO.setDifficulty(QuestionDifficultEnum.valueOf(questionEntity.getDifficult()).getDisplayName());

        return questionDTO;
    }
}
