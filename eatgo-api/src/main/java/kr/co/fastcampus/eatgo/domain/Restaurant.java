package kr.co.fastcampus.eatgo.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter //모든 속성들에 대한 getter 가 알아서 만들어 진다
@NoArgsConstructor //디폴트 생성자
@AllArgsConstructor //모든 속성을 사용하는 생성자
@Builder //Builder 패턴을 사용
public class Restaurant {
    @Id
    @Setter
    @GeneratedValue
    private Long id;
    @Setter //name에 대한 setter 가 알아서 만들어 진다
    private String name;
    private String address;

    @Transient //통과ll
    private List<MenuItem> menuItems;

    public String getInformation() {
        return name+" in "+address;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = new ArrayList<MenuItem>(menuItems);
    }

    public void setInformation(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
