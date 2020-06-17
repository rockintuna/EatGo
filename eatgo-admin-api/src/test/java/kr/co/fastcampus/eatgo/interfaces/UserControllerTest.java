package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void list() throws Exception {
        List<User> users = new ArrayList<User>();
        users.add(User.builder()
                .name("이정인")
                .email("rockintuna@naver.com")
                .level(3L)
                .build());
        given(userService.getUsers()).willReturn(users);

        mvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("이정인")));

        verify(userService).getUsers();
    }

    @Test
    public void create() throws Exception {
        String email = "rockintuna@naver.com";
        String name = "이정인";
        given(userService.addUser(email,name))
                        .willReturn(User.builder()
                                .email(email)
                                .name(name)
                                .build());

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"이정인\",\"email\":\"rockintuna@naver.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("{}"));


        verify(userService).addUser(email, name);

    }

    @Test
    public void update() throws Exception {

        mvc.perform(patch("/users/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"이정인\",\"level\":100,\"email\":\"rockintuna@naver.com\"}"))
                .andExpect(status().isOk());

        Long id = 1004L;
        String email = "rockintuna@naver.com";
        String name = "이정인";
        Long level = 100L;

        verify(userService).updateUser(id, email, name, level);

    }

    @Test
    public void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/users/1004"))
                .andExpect(status().isOk());

        Long id = 1004L;
        verify(userService).deactiveUser(id);
    }
}