package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void registerUser() {
        String email = "rockintuna@naver.com";
        String name = "이정인";
        String password = "test";
        User mockUser = User.builder().name(name).email(email).password(password).build();
        given(userRepository.save(any())).willReturn(mockUser);

        User user = userService.registerUser(email, name, password);

        assertThat(user.getName(), is("이정인"));

        verify(userRepository).save(any());
    }

    @Test(expected = EmailExistedException.class)
    public void registerUserWithExistEmail() {
        String email = "rockintuna@naver.com";
        String name = "이정인";
        String password = "test";
        User mockUser = User.builder().name(name).email(email).password(password).build();

        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));

        userService.registerUser(email, name, password);

        verify(userRepository,never()).save(any());
    }

    @Test
    public void authenticateWithValidAttributes() {
        String email = "rockintuna@naver.com";
        String password = "test";
        User mockUser = User.builder().email(email).build();

        given(userRepository.findByEmail(email))
                .willReturn(Optional.of(mockUser));
        given(passwordEncoder.matches(eq(password),any())).willReturn(true);

        User user = userService.authenticate(email,password);

        assertThat(user.getEmail(), is(email));

    }

    @Test(expected = EmailNotExistedException.class)
    public void authenticateWithNotExistedEmail() {
        String email = "notexisted@example.com";
        String password = "test";

        given(userRepository.findByEmail(email))
                .willReturn(Optional.empty());
        userService.authenticate(email,password);
    }

    @Test(expected = PasswordWrongException.class)
    public void authenticateWithWrongPassword() {
        String email = "rockintuna@naver.com";
        String password = "x";

        User mockUser = User.builder().email(email).build();

        given(userRepository.findByEmail(email))
                .willReturn(Optional.of(mockUser));
        given(passwordEncoder.matches(eq(password),any())).willReturn(false);

        userService.authenticate(email,password);

    }
}