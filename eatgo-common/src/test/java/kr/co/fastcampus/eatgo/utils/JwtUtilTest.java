package kr.co.fastcampus.eatgo.utils;

import org.junit.Test;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;

public class JwtUtilTest {

    @Test
    public void createToken() {
        String secret = "4abcdef2148724243984789347298634934";
        JwtUtil jwtUtil = new JwtUtil(secret);

        String token = jwtUtil.createToken(1004L, "이정인");

        assertThat(token, containsString("."));
    }
}