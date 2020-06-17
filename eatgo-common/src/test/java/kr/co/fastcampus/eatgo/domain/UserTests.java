package kr.co.fastcampus.eatgo.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserTests {

    @Test
    public void creation() {
        User user = User.builder()
                .name("이정인")
                .email("rockintuna@naver.com")
                .level(100L)
                .build();

        assertThat(user.getName(), is("이정인"));
        assertThat(user.isAdmin(), is(true));
    }

}