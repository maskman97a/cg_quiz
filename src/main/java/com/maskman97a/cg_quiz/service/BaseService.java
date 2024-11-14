package com.maskman97a.cg_quiz.service;

import com.maskman97a.cg_quiz.entity.BaseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BaseService {
    protected void setCreateInfo(BaseEntity baseEntity) {
        baseEntity.setCreatedAt(LocalDateTime.now());
        baseEntity.setCreatedBy("admin");
        baseEntity.setUpdatedAt(LocalDateTime.now());
        baseEntity.setUpdatedBy("admin");
    }

    protected void setUpdateInfo(BaseEntity baseEntity) {
        baseEntity.setUpdatedAt(LocalDateTime.now());
        baseEntity.setUpdatedBy("admin");
    }
}
