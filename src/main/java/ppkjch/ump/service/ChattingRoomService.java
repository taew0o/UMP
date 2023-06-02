package ppkjch.ump.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ppkjch.ump.controller.ChattingRoomController;
import ppkjch.ump.entity.ChattingRoom;
import ppkjch.ump.entity.Message;
import ppkjch.ump.entity.User;
import ppkjch.ump.entity.UserChattingRoom;
import ppkjch.ump.exception.RoomFullException;
import ppkjch.ump.repository.JpaChattingRoomRepository;
import ppkjch.ump.repository.JpaMessageRepository;
import ppkjch.ump.repository.JpaUserRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChattingRoomService {
    private final JpaChattingRoomRepository jpaChattingRoomRepository;
    private final JpaUserRepository jpaUserRepository;
    private final JpaMessageRepository jpaMessageRepository;
    public ChattingRoom findRoom(Long roomId){
        return jpaChattingRoomRepository.findOne(roomId);
    }

    @Transactional
    public Long makeRoom(List<User> users, String roomName){
        //유저 채팅방 생성
        int numPerson = users.size();
        if(numPerson > 10){
            throw new RoomFullException("10명 이하의 유저를 선택해주십시오.");
        }
        List<UserChattingRoom> userChattingRooms = new ArrayList<>();
        for (User user: users) { //각 유저ID로 User 찾아 UserChattingroom객체 만들어 매핑
            UserChattingRoom userChattingRoom = new UserChattingRoom();
            userChattingRoom.setUser(user);
            userChattingRooms.add(userChattingRoom);
        }

        //채팅방 생성
        ChattingRoom chattingRoom = ChattingRoom.createChattingroom(numPerson,userChattingRooms,roomName);
        jpaChattingRoomRepository.save(chattingRoom);
        return chattingRoom.getId();
    }

    @Transactional
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
    //회원 참여중인 채팅방 목록 조회
    public List<ChattingRoom> findRoom(User user){
        return jpaChattingRoomRepository.findChattingRoomByUser(user);
    }
    //회원 채팅방 탈퇴
    @Transactional
    public void goOutRoom(User u, ChattingRoom cr){
        jpaChattingRoomRepository.goOutRoom(u,cr);
    }

    //이 함수는 여러명 한꺼번에 초대하는 것 같긴 한데 방 객체 생성 방식 몰라서 아직 냅둠
//    public Long invite(List<String> userIds) {
//        try{
//            for(int i = 0 ; i < userIds.size() ; i++){
//
//            }
//        }catch(RoomFullException e){
//            System.out.println(e.toString());
//        }finally {
//
//        }
//    }
    public List<Message> messageTimeArray(ChattingRoom CR){
        List <Message> result = new ArrayList<>();
        result = jpaMessageRepository.findMessageByRoom(CR);
        //Comparator
        class MessageTimeComparator implements Comparator<Message> {
            @Override
            public int compare(Message m1, Message m2) {
                if (m1.getSendTime().compareTo(m2.getSendTime())>0) {
                    return 1;
                } else if (m1.getSendTime().compareTo(m2.getSendTime())<0) {
                    return -1;
                }
                return 0;
            }
        }
        MessageTimeComparator comparator = new MessageTimeComparator();
        result.sort(comparator);
        return result;
    }
}
