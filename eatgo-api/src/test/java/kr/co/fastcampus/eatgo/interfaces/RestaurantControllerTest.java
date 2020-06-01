package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.MenuItemRepository;
import kr.co.fastcampus.eatgo.domain.MenuItemRepositoryImpl;
import kr.co.fastcampus.eatgo.domain.RestaurantRepository;
import kr.co.fastcampus.eatgo.domain.RestaurantRepositoryImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) //spring을 이용하여 테스트 실행
@WebMvcTest(RestaurantController.class) //특정 컨트롤러를 테스트한다고 명시
public class RestaurantControllerTest {

    @Autowired //spring에서 알아서 넣어줄 수 있도록
    private MockMvc mvc;

    @SpyBean(RestaurantService.class)
    private RestaurantService restaurantService;

    @SpyBean(RestaurantRepositoryImpl.class) //어떤 구현체를 사용할 것인지 입력해줘야 한다
    private RestaurantRepository restaurantRepository;

    @SpyBean(MenuItemRepositoryImpl.class)
    private MenuItemRepository menuItemRepository;

    @Test
    public void list() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"Bob zip\"")
                ));
    }

    @Test
    public void detail() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"Bob zip\"")
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
}