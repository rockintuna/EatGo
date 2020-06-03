package kr.co.fastcampus.eatgo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {

    @Id //Identifier for Entity
    @GeneratedValue //1부터 순차적으로 들어감
    private Long id;

    private Long restaurantId;

    private String name;
}
