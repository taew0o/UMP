package ppkjch.ump.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ppkjch.ump.entity.ChattingRoom;
import ppkjch.ump.entity.User;
import ppkjch.ump.entity.UserChattingRoom;
import ppkjch.ump.repository.JpaChattingRoomRepository;
import ppkjch.ump.repository.JpaUserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChattingRoomService {
    private final JpaChattingRoomRepository jpaChattingRoomRepository;
    private final JpaUserRepository jpaUserRepository;

    @Transactional
    public Long makeRoom(int numPerson, List<String> userIds){
        //유저 채팅방 생성
        List<UserChattingRoom> userChattingRooms = new ArrayList<>();
        for (String userId: userIds) {
            User findUser = jpaUserRepository.findOne(userId);
            UserChattingRoom userChattingRoom = new UserChattingRoom();
            userChattingRoom.setUser(findUser);
            userChattingRooms.add(userChattingRoom);
        }

        //채팅방 생성
        ChattingRoom chattingRoom = ChattingRoom.createChattingroom(numPerson,userChattingRooms);
        jpaChattingRoomRepository.save(chattingRoom);
        return chattingRoom.getId();
    }
}
