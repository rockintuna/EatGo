package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.MenuItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class MenuItemServicesTest {

    private MenuItemService menuItemServices;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        menuItemServices = new MenuItemService(menuItemRepository);
    }


    @Test
    public void bulkUpdate() {
        List<MenuItem> menuItems = new ArrayList<MenuItem>();

        menuItems.add(MenuItem.builder().name("Kimchi").build()); //add
        menuItems.add(MenuItem.builder().id(12L).name("Gukbob").build()); //add
        menuItems.add(MenuItem.builder().id(1004L).destroy(true).build()); //delete
        menuItems.add(MenuItem.builder().id(12L).name("Guk Bob").build()); //update

        menuItemServices.bulkUpdate(1L,menuItems);

        verify(menuItemRepository, times(3)).save(any());
        verify(menuItemRepository, times(1)).deleteById(1004L);

    }

}