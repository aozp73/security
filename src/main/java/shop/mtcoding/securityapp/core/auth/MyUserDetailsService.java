package shop.mtcoding.securityapp.core.auth;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.securityapp.model.User;
import shop.mtcoding.securityapp.model.UserRepository;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // login + POST + FormUrlEncoded + username, password 일 때 아래 메서드가 실행
    // Authentication 객체 만들어짐
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 10시 10분 등 가변적인 것을 섞어서 hash화함, 따라서 매번 달라짐
        // 가변적인 hash를 사용하여 거의 못 뚫게 됨
        Optional<User> userOP = userRepository.findByUsername(username);

        if (userOP.isPresent()) {
            return new MyUserDetails(userOP.get());
        } else {
            // 비인증 (Authentication에 details를 못 넣음)
            return null;
        }

    }

}
