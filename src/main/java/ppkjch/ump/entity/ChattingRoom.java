package ppkjch.ump.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ChattingRoom {
    @Id
    @GeneratedValue
    @Column(name = "chattingroom_id")
    private Long id;

    public int numPerson = 0;

    public String chattingRoomName;

    @JsonIgnore //객체 참조용(DB엔 안들감)
    @OneToMany(mappedBy = "chattingRoom", cascade = CascadeType.ALL)
    private List<UserChattingRoom> userChattingRooms = new ArrayList<>();


    //==연관관계 메서드==//
    public void addUserChattingRoom(UserChattingRoom userChattingRoom) {
        userChattingRooms.add(userChattingRoom);
        userChattingRoom.setChattingRoom(this);
    }

    //==생성 메서드==//

    //채팅방 생성 정보 받아서 채팅방 생성 및 연관관계 설정
    public static ChattingRoom createChattingroom(int numPerson, List<UserChattingRoom> userChattingRooms, String roomName) {
        //ChattingRoom 객체만들어 속성 set하고 받은 userChattingRooms와 양방향으로 연관관계 설정(서로를 멤버로 설정)
        ChattingRoom chattingRoom = new ChattingRoom();
        chattingRoom.setNumPerson(numPerson);
        chattingRoom.setChattingRoomName(roomName);
        for (UserChattingRoom userChattingRoom : userChattingRooms) {
            chattingRoom.addUserChattingRoom(userChattingRoom);
        }
        return chattingRoom;
    }
    public void updateNumPerson(int num){
        this.numPerson += num;
    }

    public boolean isEmptyRoom(){
        return this.numPerson == 0;
    }

}


