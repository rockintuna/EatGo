package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        userService = new UserService(userRepository);
    }

    @Test
    public void getUsers() {
        List<User> mockUsers = new ArrayList<User>();
        mockUsers.add(User.builder().name("이정인").build());
        given(userRepository.findAll()).willReturn(mockUsers);

        List<User> users = userService.getUsers();
        User user = users.get(0);

        assertThat(user.getName(), is("이정인"));
    }

    @Test
    public void addUser() {
        String email = "rockintuna@naver.com";
        String name = "이정인";
        User mockUser = User.builder().email(email).name(name).build();
        given(userRepository.save(any())).willReturn(mockUser);

        User user = userService.addUser(email,name);

        assertThat(user.getName(), is(name));

        verify(userRepository).save(any()) ;
    }

    @Test
    public void updateUser() {
        Long id = 1004L;
        String name = "이인정";
        String email = "tester@test.co.kr";
        Long level = 100L;

        User mockUser = User.builder()
                .id(id)
                .email(email)
                .name("이정인")
                .level(1L)
                .build();
        given(userRepository.findById(1004L)).willReturn(Optional.of(mockUser));

        User user = userService.updateUser(id, email, name, level);

        verify(userRepository).findById(eq(id));

        assertThat(user.getName(), is("이인정"));
        assertThat(user.isAdmin(), is(true));
    }

    @Test
    public void deactiveUser() {
        User mockUser = User.builder().name("이정인").level(100L).build();
        given(userRepository.findById(1004L)).willReturn(Optional.of(mockUser));

        User user = userService.deactiveUser(1004L);

        verify(userRepository).findById(1004L);
        assertThat(user.isAdmin(), is(false));
        assertThat(user.isActive(), is(false));
    }
}