package com.maskman97a.cg_quiz.service;

import com.maskman97a.cg_quiz.dto.enums.RoleTypeEnum;
import com.maskman97a.cg_quiz.entity.RoleEntity;
import com.maskman97a.cg_quiz.entity.UserEntity;
import com.maskman97a.cg_quiz.entity.UserRoleEntity;
import com.maskman97a.cg_quiz.exception.ValidateException;
import com.maskman97a.cg_quiz.repository.RoleRepository;
import com.maskman97a.cg_quiz.repository.UserRepository;
import com.maskman97a.cg_quiz.repository.UserRoleRepository;
import com.maskman97a.cg_quiz.utils.DataUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService extends BaseService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public void register(HttpServletRequest httpServletRequest, String username, String password) throws ValidateException {
        if (DataUtils.isNullOrEmpty(username)) {
            throw new ValidateException("Tên đăng nhập không được để trống");
        }
        if (DataUtils.isNullOrEmpty(password)) {
            throw new ValidateException("Mật khẩu không được để trống");
        }
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsernameIgnoreCase(username);
        if (optionalUserEntity.isPresent()) {
            throw new ValidateException("Tên đăng nhập đã tồn tại");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(password);
        userEntity = userRepository.save(userEntity);
        RoleEntity roleEntity = new RoleEntity();
        Optional<RoleEntity> optionalRoleEntity = roleRepository.findByType(RoleTypeEnum.DEFAULT);
        if (optionalRoleEntity.isPresent()) {
            roleEntity = optionalRoleEntity.get();
        } else {
            roleEntity.setType(RoleTypeEnum.DEFAULT);
            roleEntity.setName(RoleTypeEnum.DEFAULT.getName());
            roleEntity.setCode(RoleTypeEnum.DEFAULT.name());
            roleEntity = roleRepository.save(roleEntity);
        }
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserId(userEntity.getId());
        userRoleEntity.setRoleId(roleEntity.getId());
        userRoleRepository.save(userRoleEntity);
    }
}
