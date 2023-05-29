package ppkjch.ump.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
public class FriendRequest {
    @Id
    @GeneratedValue
    @Column(name = "friend_request_id")
    private Long id;

    @JoinColumn(name = "sender")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User sender;

    @JoinColumn(name = "receiver")
    @ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User receiver;
}
