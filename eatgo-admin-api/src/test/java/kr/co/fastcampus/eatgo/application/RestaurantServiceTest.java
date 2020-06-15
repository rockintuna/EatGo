package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class RestaurantServiceTest {

    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockRestaurantRepository();

        restaurantService = new RestaurantService(restaurantRepository);
    }

    private void mockRestaurantRepository() {

        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();

        restaurants.add(restaurant);

        given(restaurantRepository.findAll()).willReturn(restaurants);

        given(restaurantRepository.findById(1004L)).willReturn(java.util.Optional.of(restaurant));

    }


    @Test
    public void getRestaurants() {

        List<Restaurant> restaurants = restaurantService.getRestaurants();

        Restaurant restaurant = restaurants.get(0);
        assertThat(restaurant.getId(), is(1004L));
    }

    @Test
    public void getRestaurantWithExist() {

        Restaurant restaurant = restaurantService.getRestaurant(1004L);
        assertThat(restaurant.getId(), is(1004L));

    }

    @Test(expected = RestaurantNotFoundException.class) //요청만해도 예외가 발생하길 원한다.
    public void getRestaurantWithNotExist() {

        Restaurant restaurant = restaurantService.getRestaurant(404L);
    }

    @Test
    public void addRestaurant() {

        given(restaurantRepository.save(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            restaurant.setId(1234L);
            return restaurant;
        });

        Restaurant restaurant = Restaurant.builder()
                .name("BeRyong")
                .address("Busan")
                .build();

        Restaurant created = restaurantService.addRestaurant(restaurant);

        assertThat(created.getId(), is(1234L));
    }

    @Test
    public void updateRestaurantWithExist() {

        Restaurant restaurant = Restaurant.builder()
                .id(1L)
                .name("BobZip")
                .address("Seoul")
                .build();
        given(restaurantRepository.findById(1L)).willReturn(java.util.Optional.of(restaurant));

        restaurantService.updateRestaurant(1L,"GukBobZip","Busan");

        assertThat(restaurant.getName(), is("GukBobZip"));
        assertThat(restaurant.getAddress(), is("Busan"));
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void updateRestaurantWithNotExist() {

        restaurantService.updateRestaurant(404L,"GukBobZip","Busan");
    }
}