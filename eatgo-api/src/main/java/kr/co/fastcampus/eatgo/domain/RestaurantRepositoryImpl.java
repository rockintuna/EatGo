package kr.co.fastcampus.eatgo.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component //Spring이 직접 관리하도록
public class RestaurantRepositoryImpl implements RestaurantRepository {
    List<Restaurant> restaurants = new ArrayList<>(); //if문을 쓰지 않기 위해 List로 가게 관리

    public RestaurantRepositoryImpl() {
        restaurants.add(new Restaurant(1004L,"Bob zip","Seoul"));
        restaurants.add(new Restaurant(2020L,"Cyber Food","Seoul"));
    }

    @Override
    public List<Restaurant> findAll() {
        return restaurants;
    }

    @Override
    public Restaurant findById(Long id) {
        return restaurants.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

}
