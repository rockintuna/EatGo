package kr.co.fastcampus.eatgo.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CategoryTests {

    @Test
    public void creation() {
        Category category = Category.builder().name("서울").build();

        assertThat(category.getName(), is("서울"));
    }

}