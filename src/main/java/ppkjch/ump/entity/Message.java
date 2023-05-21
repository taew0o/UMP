package ppkjch.ump.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Message {
    @Id
    @GeneratedValue
    @Column(name = "message_id")
    private Long id;

    private String textMsg;

    @ManyToOne(targetEntity = ChattingRoom.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="chattingRoom_id")
    private ChattingRoom chattingRoom;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime sendTime;

}
