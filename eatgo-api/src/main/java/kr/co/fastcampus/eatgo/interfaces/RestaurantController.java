package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public List<Restaurant> list() {

        List<Restaurant> restaurants = restaurantService.getRestaurants(); //API들의 중복제거를 위해 가게 저장소 사용
        return restaurants;
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant detail(@PathVariable("id") Long id) {

        Restaurant restaurant = restaurantService.getRestaurant(id); //기본 정보 + 메뉴 정보를 가져올 새로운 메서드
        //repository가 저장소 역할을 했다면, 복잡한 처리들을 저장할 application layer

        return restaurant;
    }

}
