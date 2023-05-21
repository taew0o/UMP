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

//@Entity
//@Table(name = "friend")
//public class Friend {
//    @EmbeddedId
//    private FriendId id;
//
//
//    @ManyToOne
//    @MapsId("userId1")
//    @JoinColumn(name = "user_id_1")
//    private User user1;
//
//    @ManyToOne
//    @MapsId("userId2")
//    @JoinColumn(name = "user_id_2")
//    private User user2;
//
//}
//
//@Embeddable
//class FriendId implements Serializable {
//    @Column(name = "user_id_1")
//    private Long userId1;
//
//    @Column(name = "user_id_2")
//    private Long userId2;
//
//}