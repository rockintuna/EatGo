package kr.co.fastcampus.eatgo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MenuItem {

    @Id //Identifier for Entity
    @GeneratedValue //1부터 순차적으로 들어감
    private Long id;

    private Long restaurantId;

    private final String name;

    public MenuItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
