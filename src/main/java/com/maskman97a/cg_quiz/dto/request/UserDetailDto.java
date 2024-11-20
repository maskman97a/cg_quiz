package com.maskman97a.cg_quiz.dto.request;


import com.maskman97a.cg_quiz.dto.enums.RoleTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailDto implements UserDetails {
    private String fullName;
    private String email;
    private String password;
    private List<GrantedAuthority> authorities;
    private Set<String> roles;
    public UserDetailDto(List<GrantedAuthority> authorities, String password, String email, String fullName) {
        this.authorities = authorities;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return email;
    }

    public boolean hasRole(String role) {
       if (this.roles.contains(role)) {
           return true ;
       } else {
           return false ;
       }
    }
}
