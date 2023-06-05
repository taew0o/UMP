package ppkjch.ump.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserChattingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_chattingroom_id")
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private User user;

    @JoinColumn(name = "chattingroom_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private ChattingRoom chattingRoom;

    private Long enterTime;

    private int numAttend;
    private int numNotAttend;
    private int numLate;

    public void sumScore(int numAttend, int numNotAttend, int numLate){
        this.numAttend += numAttend;
        this.numNotAttend += numNotAttend;
        this.numLate += numLate;
    }

    @Override
    public String toString() {
        return "UserChattingRoom{" +
                "id=" + id +
                ", user=" + user +
                ", chattingRoom=" + chattingRoom +
                '}';
    }


}
