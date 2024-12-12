package com.maskman97a.cg_quiz.config;

import com.maskman97a.cg_quiz.dto.UserDetailDto;
import com.maskman97a.cg_quiz.entity.UserEntity;
import com.maskman97a.cg_quiz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<UserEntity> optionalUserEntity = userRepository.findByEmailIgnoreCase(username);
        if (optionalUserEntity.isPresent() && optionalUserEntity.get().getPassword().equals(password)) {

            GrantedAuthority role = new SimpleGrantedAuthority(optionalUserEntity.get().getUserType().name());
            UserDetailDto userDetails = new UserDetailDto();
            userDetails.setId(optionalUserEntity.get().getId());
            userDetails.setAuthorities(List.of(role));
            userDetails.setEmail(username);
            userDetails.setFullName(optionalUserEntity.get().getFullName());
            userDetails.setAvatar(optionalUserEntity.get().getAvatar());
            userDetails.setRole(optionalUserEntity.get().getUserType().name());
            return new UsernamePasswordAuthenticationToken(userDetails, password, List.of(role));
        }
        throw new AuthenticationException("Authentication failed") {
        };
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
