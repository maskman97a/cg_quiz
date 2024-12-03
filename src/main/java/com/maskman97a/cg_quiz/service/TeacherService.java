package com.maskman97a.cg_quiz.service;

import com.maskman97a.cg_quiz.entity.TeacherEntity;
import com.maskman97a.cg_quiz.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    public List<TeacherEntity> getList() {
        return teacherRepository.findAll();
    }

    public TeacherEntity createTeacher(String teacherName, Integer teacherAge) {
        boolean isValid = validateTeacher(teacherName, teacherAge);
        if (isValid) {
            TeacherEntity teacherEntity = new TeacherEntity();
            teacherEntity.setName(teacherName);
            teacherEntity.setAge(teacherAge);
            teacherRepository.save(teacherEntity);
        }
        return null;
    }

    private boolean validateTeacher(String teacherName, Integer teacherAge) {
        //Logic validate teacher
        return true;
    }

    public TeacherEntity getTeacherById(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    public TeacherEntity updateTeacher(Long id, String teacherName, Integer teacherAge) {
        TeacherEntity teacherEntity = teacherRepository.findById(id).orElse(null);
        if (teacherEntity != null) {
            teacherEntity.setName(teacherName);
            teacherEntity.setAge(teacherAge);
            teacherEntity = teacherRepository.save(teacherEntity);
        }
        return teacherEntity;
    }

    public void deleteTeacher(Long id) {
        TeacherEntity teacherEntity = teacherRepository.findById(id).orElse(null);
        if (teacherEntity != null) {
            teacherRepository.delete(teacherEntity);
        }
    }
}
