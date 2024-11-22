package com.maskman97a.cg_quiz.service;

import com.maskman97a.cg_quiz.entity.UserEntity;
import com.maskman97a.cg_quiz.exception.ValidateException;
import com.maskman97a.cg_quiz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService extends BaseService {
    private final UserRepository userRepository;

    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmailIgnoreCase(email).orElse(null);
    }


    public void update(String fullName, MultipartFile avatar) throws IOException {
        UserEntity userEntity = getCurrentUser();
        if (userEntity != null) {
            userEntity.setFullName(fullName);
            if (avatar != null) {
                userEntity.setAvatar(avatar.getBytes());
            }
            userRepository.save(userEntity);
        }
    }

    public void changePassword(String currentPassword, String newPassword) throws ValidateException {
        UserEntity userEntity = getCurrentUser();
        if (userEntity != null) {
            if (!userEntity.getPassword().equals(currentPassword)) {
                throw new ValidateException("Mật khẩu hiện tại không đúng");
            }
            if (currentPassword.equals(newPassword)) {
                throw new ValidateException("Mật khẩu mới trùng mật khẩu hiện tại");
            }
            userEntity.setPassword(newPassword);
            userRepository.save(userEntity);
        }
    }


}
