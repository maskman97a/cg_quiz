package com.maskman97a.cg_quiz.config;

import com.maskman97a.cg_quiz.dto.RoleDto;
import com.maskman97a.cg_quiz.dto.UserDetailDto;
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
        if (optionalUserEntity.isPresent() && optionalUserEntity.get().getPassword().equals(password)) {
            Set<RoleDto> roleDtos = userRoleRepository.getUserRole(optionalUserEntity.get().getId());
            List<GrantedAuthority> authorities = roleDtos.stream()
                    .map(x -> new SimpleGrantedAuthority(x.getType())).collect(Collectors.toList());
            UserDetailDto userDetails = new UserDetailDto();
            userDetails.setId(optionalUserEntity.get().getId());
            userDetails.setAuthorities(authorities);
            userDetails.setEmail(username);
            userDetails.setFullName(optionalUserEntity.get().getFullName());
            userDetails.setAvatar(optionalUserEntity.get().getAvatar());
            return new UsernamePasswordAuthenticationToken(userDetails, password, authorities);
        }
        throw new AuthenticationException("Authentication failed") {
        };
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
