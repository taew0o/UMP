package ppkjch.ump.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
public class Friend {
    @Id
    @GeneratedValue
    @Column(name = "friend_id")
    private Long id;

    @JoinColumn(name = "user_id1")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user1;

    @JoinColumn(name = "user_id2")
    @ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user2;

}
