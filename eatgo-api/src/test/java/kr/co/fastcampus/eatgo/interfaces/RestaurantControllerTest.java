package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    //Test하려는 대상은 RestaurantController 이므로 그 외의 대상을 가짜로 배치하려고 함
    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void list() throws Exception {

        //가짜 객체 생성, 가짜 처리
        //실제 서비스, 저장소와는 무관하게 동작
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(Restaurant.builder()
                .id(1004L)
                .name("No Bob zip")
                .address("Seoul")
                .build());
        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mvc.perform(MockMvcRequestBuilders.get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"No Bob zip\"")
                ));
    }

    @Test
    public void detail() throws Exception {
        Restaurant restaurant1 = Restaurant.builder()
                .id(1004L)
                .name("No Bob zip")
                .address("Seoul")
                .build();
        restaurant1.setMenuItems(Arrays.asList(MenuItem.builder()
                .name("Kimchi")
                .build()));
        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant1);

        Restaurant restaurant2 = Restaurant.builder()
                .id(2020L)
                .name("Cyber Food")
                .address("Seoul")
                .build();
        given(restaurantService.getRestaurant(2020L)).willReturn(restaurant2);

        mvc.perform(MockMvcRequestBuilders.get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"No Bob zip\"")
                ))
                .andExpect(content().string(
                        containsString("Kimchi")
                ));

        mvc.perform(MockMvcRequestBuilders.get("/restaurants/2020"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":2020")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"Cyber Food\"")
                ));

    }

    @Test
    public void create() throws Exception {
        given(restaurantService.addRestaurant(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            return Restaurant.builder()
                    .id(1234L)
                    .name(restaurant.getName())
                    .address(restaurant.getAddress())
                    .build();
        });

        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON) //JSON 타입임을 알려준다
                .content("{\"name\":\"BeRyong\",\"address\":\"Busan\"}")) //JSON 타입으로 데이터 전달
                .andExpect(status().isCreated())
                .andExpect(content().string("{}")); //비어있는지 확인

        verify(restaurantService).addRestaurant(any()); //뭘 넣든지 작동할 수 있도록, Mockito에서 제공
    }

    @Test
    public void update() throws Exception {
        mvc.perform(patch("/restaurants/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"GukBobZip\",\"address\":\"Busan\"}"))
                .andExpect(status().isOk());

        verify(restaurantService).updateRestaurant(1L, "GukBobZip", "Busan");
    }
}