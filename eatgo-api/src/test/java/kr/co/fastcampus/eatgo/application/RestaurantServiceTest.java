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
import static org.mockito.Mockito.verify;

public class RestaurantServiceTest {

    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private MenuItemRepository menuItemRepository;
    @Mock
    private ReviewRepository reviewRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockRestaurantRepository();
        mockMenuItemRepository();
        mockReviewRepository();

        restaurantService = new RestaurantService(restaurantRepository,menuItemRepository, reviewRepository);
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

    private void mockMenuItemRepository() {

        List<MenuItem> menuItems = new ArrayList<>();
        MenuItem menuItem = MenuItem.builder()
                .name("Kimchi")
                .build();
        menuItems.add(menuItem);

        given(menuItemRepository.findAllByRestaurantId(1004L)).willReturn(menuItems);

    }

    private void mockReviewRepository() {

        List<Review> reviews = new ArrayList<Review>();
        reviews.add(Review.builder().name("jilee").score(3).description("thanks").build());

        given(reviewRepository.findAllByRestaurantId(1004L)).willReturn(reviews);
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

        verify(menuItemRepository).findAllByRestaurantId(1004L);
        verify(reviewRepository).findAllByRestaurantId(1004L);

        MenuItem menuItem = restaurant.getMenuItems().get(0);
        assertThat(menuItem.getName(), is("Kimchi"));

        Review review = restaurant.getReviews().get(0);
        assertThat(review.getScore(), is(3));
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