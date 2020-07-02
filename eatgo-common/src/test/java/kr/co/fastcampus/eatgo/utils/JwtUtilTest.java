package kr.co.fastcampus.eatgo.utils;

import io.jsonwebtoken.Claims;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;

public class JwtUtilTest {

    private static final String SECRET = "4abcdef2148724243984789347298634934";
    private JwtUtil jwtUtil;

    @Before
    public void setUp() {
        jwtUtil = new JwtUtil(SECRET);
    }

    @Test
    public void createToken() {
        String token = jwtUtil.createToken(1004L, "이정인");

        assertThat(token, containsString("."));
    }

    @Test
    public void getClaims() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsIm5hbWUiOiLsnbTsoJXsnbgifQ.wKrAIT-cOCfQwBO_8UkhK_IOy4tl8uuIeBS4nppu_Vw";

        Claims claims = jwtUtil.getClaims(token);

        assertThat(claims.get("name"), is("이정인"));
        assertThat(claims.get("userId", Long.class), is(1004L));
    }
}