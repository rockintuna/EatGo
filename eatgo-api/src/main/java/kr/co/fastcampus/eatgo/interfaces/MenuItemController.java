package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.MenuItemService;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemServices;

    @GetMapping("/restaurants/{restaurantId}/menuitems")
    public List<MenuItem> list() {

        List<MenuItem> menuItems = null;
        return menuItems;
    }

    @PatchMapping("/restaurants/{restaurantId}/menuitems")
    public List<MenuItem> bulkUpdate(
            @PathVariable("restaurantId") Long restaurantId,
            @RequestBody List<MenuItem> menuItems
    ) {
        menuItemServices.bulkUpdate(restaurantId, menuItems);
        return menuItems;
    }

}
