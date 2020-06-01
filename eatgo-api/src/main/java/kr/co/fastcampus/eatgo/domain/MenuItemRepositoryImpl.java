package kr.co.fastcampus.eatgo.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuItemRepositoryImpl implements MenuItemRepository{

    private List<MenuItem> menuitems = new ArrayList<MenuItem>();

    public MenuItemRepositoryImpl() {
        menuitems.add(new MenuItem("Kimchi"));
    }

    @Override
    public List<MenuItem> findAllByRestaurantId(Long restaurantId) {
        return this.menuitems;
    }
}
