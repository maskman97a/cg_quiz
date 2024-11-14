package com.maskman97a.cg_quiz.config;

import com.maskman97a.cg_quiz.dto.RoleDto;
import com.maskman97a.cg_quiz.dto.UserDetailDto;
import com.maskman97a.cg_quiz.dto.enums.UserTypeEnum;
import com.maskman97a.cg_quiz.entity.UserEntity;
import com.maskman97a.cg_quiz.repository.UserRepository;
import com.maskman97a.cg_quiz.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<UserEntity> optionalUserEntity = userRepository.findByEmailIgnoreCase(username);
        // Kiểm tra thông tin đăng nhập
        if (optionalUserEntity.isPresent() && optionalUserEntity.get().getPassword().equals(password)) {
            UserEntity userEntity = optionalUserEntity.get();

            // Lấy loại tài khoản từ UserEntity
            UserTypeEnum userType = userEntity.getUserType();

            // Tạo quyền (GrantedAuthority) từ userType
            GrantedAuthority authority = new SimpleGrantedAuthority(userType.name());

            // Tạo đối tượng UserDetailDto
            UserDetailDto userDetails = new UserDetailDto();
            userDetails.setEmail(username);
            userDetails.setPassword(password);
            userDetails.setFullName(userEntity.getFullName());
            userDetails.setAuthorities(List.of(authority));

            // Trả về đối tượng Authentication
            return new UsernamePasswordAuthenticationToken(userDetails, password, List.of(authority));
        }

        // Nếu thất bại, ném ngoại lệ
        throw new AuthenticationException("Authentication failed") {};
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
