package ppkjch.ump.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
public class User{
    @Id
    @NotNull
    @Column(name = "user_id")
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String password;

    //객체 참조용(DB엔 안들감)
    @OneToMany(mappedBy = "user1", fetch = FetchType.LAZY) //friend 테이블의 user1에 매핑된 리스트
    private List<Friend> friends1 = new ArrayList<>();

    @OneToMany(mappedBy = "user2", fetch = FetchType.LAZY) //friend 테이블의 user2에 매핑된 리스트
    private List<Friend> friends2 = new ArrayList<>();
}
