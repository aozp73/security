package shop.mtcoding.securityapp.core.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import shop.mtcoding.securityapp.model.User;

@Getter
public class MyUserDetails implements UserDetails {

    // 컴포지션 해서 그림에서 principal에 값을 설정해줄 수 있음
    private User user;

    public MyUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> user.getRole());
        // authorities.add(() -> user.getRole());
        // authorities.add(() -> user.getRole());

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 탈퇴하면 status false로 할거임
        // 이후 가입 불가
        return user.getStatus();
    }
}
