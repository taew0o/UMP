package ppkjch.ump.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ppkjch.ump.dto.InviteDTO;
import ppkjch.ump.entity.ChattingRoom;
import ppkjch.ump.entity.User;
import ppkjch.ump.entity.UserChattingRoom;
import ppkjch.ump.exception.RoomFullException;
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
    public Long makeRoom(List<String> userIds){
        //유저 채팅방 생성
        int numPerson = userIds.size();
        List<UserChattingRoom> userChattingRooms = new ArrayList<>();
        for (String userId: userIds) { //각 유저ID로 User 찾아 UserChattingroom객체 만들어 매핑
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

    public Long inviteRoom(Long roomId, String inviteeId) {
        //ID 정보로 엔티티 조회
        ChattingRoom findRoom = jpaChattingRoomRepository.findOne(roomId);
        //방이 full인지 확인
        if(findRoom.getNumPerson() == 10){
            throw new RoomFullException("10명인 방에는 초대할 수 없습니다.");
        }
        User findUser = jpaUserRepository.findOne(inviteeId);

        //UserChattingRoom 객체 만들고 연관관계 매핑
        UserChattingRoom userChattingRoom = new UserChattingRoom();
        userChattingRoom.setUser(findUser);
        findRoom.addUserChattingRoom(userChattingRoom); //persist 안해도 자동 변경감지됨

        return roomId;
    }

//    public Long invite(List<String> userIds) {
//    }
}
