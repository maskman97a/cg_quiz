package com.maskman97a.cg_quiz.service;

import com.maskman97a.cg_quiz.dto.enums.UserTypeEnum;
import com.maskman97a.cg_quiz.entity.BaseEntity;
import com.maskman97a.cg_quiz.entity.UserEntity;
import com.maskman97a.cg_quiz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService extends BaseEntity {
    private UserRepository userRepository;
    public void getListStudentAccount() {
        Pageable pageable = Pageable.ofSize(20);
        List<UserEntity> studentAccountList = userRepository.findByUserTypeOrderByCreatedAtDesc(pageable, UserTypeEnum.STUDENT);
    }
}
