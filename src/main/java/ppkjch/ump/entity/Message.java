package ppkjch.ump.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
public class Message {
    @Id
    @GeneratedValue
    @Column(name = "message_id")
    private Long id;

    private String textMsg;

    @ManyToOne(targetEntity = ChattingRoom.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name="chattingRoom_id")
    private ChattingRoom chattingRoom;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name = "send_time")
    private Long sendTime;

    public static Message createMessage(String text, User user,ChattingRoom chattingRoom, Long sendTime) {
        Message message = new Message();
        message.setSendTime(sendTime);
        message.setTextMsg(text);
        message.setUser(user);
        message.setChattingRoom(chattingRoom);
        return message;
    }
}
