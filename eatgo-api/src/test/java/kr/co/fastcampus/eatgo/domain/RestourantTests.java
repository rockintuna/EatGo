package kr.co.fastcampus.eatgo.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class RestourantTests {

    @Test
    public void creation() {
        Restourant restourant = new Restourant("Bob zip","Seoul");
        assertThat(restourant.getName(), is("Bob zip"));
        assertThat(restourant.getAddress(), is("Seoul"));
    }

    @Test
    public void information() {
        Restourant restourant = new Restourant("Bob zip","Seoul");
        assertThat(restourant.getInformation(), is("Bob zip in Seoul"));
    }
}