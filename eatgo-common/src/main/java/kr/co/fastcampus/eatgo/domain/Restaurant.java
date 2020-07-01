package kr.co.fastcampus.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {

    @Id
    @Setter
    @GeneratedValue
    private Long id;

    @Setter
    @NotEmpty
    private String name;

    @NotEmpty
    private String address;

    @NotNull
    private Long categoryId;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL) //Null이 아닐때만 json에 넣어줘라
    private List<MenuItem> menuItems;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL) //Null이 아닐때만 json에 넣어줘라
    private List<Review> reviews;


    public String getInformation() {
        return name+" in "+address;
    }

    public void setInformation(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = new ArrayList<MenuItem>(menuItems);
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = new ArrayList<>(reviews);
    }
}
