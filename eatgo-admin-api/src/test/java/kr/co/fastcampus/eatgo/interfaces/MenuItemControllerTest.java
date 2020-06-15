package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.MenuItemService;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MenuItemController.class)
public class MenuItemControllerTest {

    @MockBean
    MenuItemService menuItemServices;

    @Autowired
    private MockMvc mvc;

    @Test
    public void list() throws Exception {
        List<MenuItem> menuitems = new ArrayList<MenuItem>();
        menuitems.add(MenuItem.builder().name("Kimchi").build());

        given(menuItemServices.getMenuItem(1L))
                .willReturn(menuitems);

        mvc.perform(get("/restaurants/1/menuitems"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Kimchi")));

        verify(menuItemServices).getMenuItem(1L);
    }



    @Test
    public void bulkUpdate() throws Exception {
        mvc.perform(patch("/restaurants/1/menuitems") //1번 가게의 menuitem을 bulk로 수정할껀데
                .contentType(MediaType.APPLICATION_JSON) //헤더에 content-Type이 Application/json 이고
                .content("[\n" +
                        "  {\n" +
                        "    \"name\": \"Kimchi\"\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"name\": \"Gukbob\"\n" +
                        "  }\n" +
                        "]")) //이런 array를 입력할꺼야
                .andExpect(status().isOk()); //http status 200이야?

        verify(menuItemServices).bulkUpdate(eq(1L), any()); //서비스에서 bulkUpdate()가 올바른 id(1L)로 호출되나?
    }
}