package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.ReservationService;
import kr.co.fastcampus.eatgo.domain.Reservation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    public void create() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsIm5hbWUiOiLsnbTsoJXsnbgifQ.wKrAIT-cOCfQwBO_8UkhK_IOy4tl8uuIeBS4nppu_Vw";

        Long userId = 1004L;
        String name = "이정인";
        String date = "2019-12-24";
        String time = "20:00";
        Integer partySize = 20;
        Reservation reservation = Reservation.builder()
                .date(date)
                .time(time)
                .partySize(partySize)
                .build();

        given(reservationService.addReservation(369L,userId,name,date,time,partySize))
                .willReturn(reservation);

        mvc.perform(post("/restaurants/369/reservations")
                .header("Authorization", "Bearer "+token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\"date\":\"2019-12-24\",\"time\":\"20:00\"," +
                        "\"partySize\":20}"))
                .andExpect(status().isCreated());

        verify(reservationService).addReservation(
                369L,userId, name, date, time, partySize);
    }

}