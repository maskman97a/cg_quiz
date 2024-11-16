package com.maskman97a.cg_quiz.dto;

import com.maskman97a.cg_quiz.dto.enums.RoleTypeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailDto implements UserDetails {
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private List<GrantedAuthority> authorities;
    private Set<String> roles;
    private byte[] avatar;

    public UserDetailDto(String fullName, String password, List<GrantedAuthority> authorities) {
        this.fullName = fullName;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public boolean hasRole(String role) {
        if (this.roles.contains(role)) {
            return true;
        } else {
            return false;
        }
    }


}
