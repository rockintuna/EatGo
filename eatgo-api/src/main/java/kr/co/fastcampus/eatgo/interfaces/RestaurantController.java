package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //Rest API 를 사용하는 컨트롤러 임을 명시
public class RestaurantController {

    @Autowired //Spring이 알아서 객체를 생성해서 멤버변수에 넣어준다.
    private RestaurantRepository repository;

    //UI 레이어는 사용자와 내부의 비즈니스 로직, 도메인 모델()들이 상관 없도록 중간 다리 역할만 하는 것이 좋다

    @GetMapping("/restaurants")
    public List<Restaurant> list() {

        List<Restaurant> restaurants = repository.findAll(); //API들의 중복제거를 위해 가게 저장소 사용
        return restaurants;
    }

    @GetMapping("/restaurants/{id}") //{}로 바뀌는 부분을 매핑가능
    public Restaurant detail(@PathVariable("id") Long id) { //주소의 파라미터를 활용할 수 있음

        Restaurant restaurant = repository.findById(id); //저장소에서 기능을 생성하여 사용
        return restaurant;
    }

}
