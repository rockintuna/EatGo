package kr.co.fastcampus.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Setter
    private String email;

    @NotEmpty
    @Setter
    private String name;

    @NotNull
    @Setter
    private Long level;

    private String password;

    public boolean isAdmin() {
        return level >= 100;
    }

    public boolean isActive() {
        return level > 0;
    }

    public void deactivate() {
        level = 0L;
    }

    //@JsonIgnore //requestedbody에 password가 없는경우 무시
    public String getAccessToken() {
        if (password == null) {
            return "";
        }
        return password.substring(0, 10);
    }
}
