package ppkjch.ump.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ChattingRoom {
    @Id
    @GeneratedValue
    @Column(name = "chattingroom_id")
    private Long id;

    public int numPerson = 0;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createTime;

}


