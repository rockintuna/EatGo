package kr.co.fastcampus.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {

    @Id //Identifier for Entity
    @GeneratedValue //1부터 순차적으로 들어감
    private Long id;

    @Setter
    private Long restaurantId;

    @Setter
    private String name;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_DEFAULT) //Json에 포함 X
    private boolean destroy;

}
