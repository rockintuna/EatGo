package kr.co.fastcampus.eatgo.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

    public interface MenuItemRepository
            extends CrudRepository<MenuItem, Long> { //인터페이스만 이용하여 JPA와 연결하여 사용 <T, ID>
                                                     //T : Entity의 타입, ID : id의 타입
        List<MenuItem> findAllByRestaurantId(Long restaurantId);

    }
