package kr.co.fastcampus.eatgo.interfaces;

import io.jsonwebtoken.Claims;
import kr.co.fastcampus.eatgo.application.ReservationService;
import kr.co.fastcampus.eatgo.domain.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/restaurants/{restaurantId}/reservations")
    public ResponseEntity<?> create(
            Authentication authentication,
            @RequestBody Reservation resource,
            @Valid @PathVariable("restaurantId") Long restaurantId
    ) throws URISyntaxException {
        Claims claims = (Claims) authentication.getPrincipal();

        Reservation reservation = reservationService.addReservation(
                restaurantId,
                claims.get("userId", Long.class),
                claims.get("name", String.class),
                resource.getDate(),
                resource.getTime(),
                resource.getPartySize()
        );

        String url = "/restaurants/"+restaurantId+"/reservations"+reservation.getId();

        return ResponseEntity.created(new URI(url)).body("{}");
    }
}