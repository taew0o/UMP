package ppkjch.ump.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ppkjch.ump.dto.GetMessageDTO;
import ppkjch.ump.dto.MakeRoomDTO;
import ppkjch.ump.dto.InviteRoomDTO;
import ppkjch.ump.dto.RoomIdDTO;
import ppkjch.ump.entity.ChattingRoom;
import ppkjch.ump.entity.Message;
import ppkjch.ump.entity.User;
import ppkjch.ump.entity.UserChattingRoom;
import ppkjch.ump.exception.RoomFullException;
import ppkjch.ump.service.ChattingRoomService;
import ppkjch.ump.service.MessageService;
import ppkjch.ump.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000")
public class ChattingRoomController {
    private final ChattingRoomService chattingRoomService;
    private final UserService userService;
    private final MessageService messageService;

    @PostMapping("/chattingroom")
    public ResponseEntity<?> makeChattingRoom(HttpServletRequest request, @RequestBody MakeRoomDTO roomInfo){
        // 세션에서 유저 ID 가져오기
        HttpSession session = request.getSession(false);
        String userId = (String)session.getAttribute("userId");
        // 유저 ID를 사용하여 유저 정보 조회
        User user = userService.findUser(userId);
        ArrayList<User> users = new ArrayList<>();
        users.add(user);

        for (String memberId: roomInfo.getUserIds()) {
            User findUser = userService.findUser(memberId);
            users.add(findUser);
        }
        //유저 정보로 채팅방 만들기
        try{
            Long chattingRoomId = chattingRoomService.makeRoom(users, roomInfo.getRoomName(), roomInfo.getCreateTime());
            return new ResponseEntity<Long>(chattingRoomId, HttpStatus.OK);
        }
        catch (RoomFullException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/chattingrooms")
    public ResponseEntity<List<ChattingRoom>> getChattingRooms(HttpServletRequest request){
        // 세션에서 유저 ID 가져오기
        HttpSession session = request.getSession(false);
        String userId = (String)session.getAttribute("userId");
        // 유저 ID를 사용하여 유저 정보 조회
        User user = userService.findUser(userId);
        //findRoom(User user): List<CattingRoom> : 태우 추가 완. 테스트 필요\
        List<ChattingRoom> chattingRooms = chattingRoomService.findRoom(user);
        return ResponseEntity.status(HttpStatus.OK).body(chattingRooms);
    }

    @PostMapping("/chattingroom/member")
    public ResponseEntity<String> inviteChattingRoom(@RequestBody InviteRoomDTO inviteDTO){
        //유저 ID정보로 채팅방에 user정보 추가하고 추가된 방을 반환
        System.out.println("inviteDTO.getEnterTime() = " + inviteDTO.getEnterTime());
        try {
            List<User> invitees = new ArrayList<>();
            for (String id: inviteDTO.getInviteeIds()) {
                User invitee = userService.findUser(id);
                invitees.add(invitee);
            }
            ChattingRoom chattingRoom = chattingRoomService.findRoom(inviteDTO.getRoomId());
            chattingRoomService.inviteRoom(chattingRoom, invitees, inviteDTO.getEnterTime());

        } catch (RoomFullException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("성공적으로 초대");

    }
    @GetMapping("/chattingroom/members")
    public ResponseEntity<List<User>> getMembers(@RequestBody RoomIdDTO roomIdDTO){
        //방 id로 방 찾기
        Long roomId = roomIdDTO.getRoomId();
        ChattingRoom room = chattingRoomService.findRoom(roomId);

        //방에 있는 유저들 찾기
        List<User> members = chattingRoomService.findRoomMember(room);

        //찾으 유저 정보 넘겨주기
        return ResponseEntity.status(HttpStatus.CREATED).body(members);
    }

    @DeleteMapping("/chattingroom/member")
    public ResponseEntity<?> goOutChattingRoom(HttpServletRequest request, @RequestParam("roomId") Long roomId){
        // 세션에서 유저 ID 가져오기
        HttpSession session = request.getSession(false);
        String userId = (String)session.getAttribute("userId");
        // 유저 ID를 사용하여 유저 정보 조회
        User user = userService.findUser(userId);
        //방ID 사용하여 방 조회
        ChattingRoom room = chattingRoomService.findRoom(roomId);
        //goOutRoom(User, ChattingRoom): void - 태우 추가 완. 테스트 필요

        chattingRoomService.goOutRoom(user,room);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/chattingroom/messages")
    public ResponseEntity<List<GetMessageDTO>> getMessages(HttpServletRequest request, @RequestParam("roomId") Long roomId){
        // 세션에서 유저 ID 가져오기
        HttpSession session = request.getSession(false);
        String userId = (String)session.getAttribute("userId");
        // 유저 ID를 사용하여 유저 정보 조회
        User user = userService.findUser(userId);
        ChattingRoom chattingRoom = chattingRoomService.findRoom(roomId);
        //해당 방 ID로 유저채팅룸 객체 가져와서 user의 입장 시간 알아내기 (나중에 리팩토링 필요할듯)
        List<UserChattingRoom> userChattingRooms = chattingRoom.getUserChattingRooms();
        Long enterTime = null;

        for (UserChattingRoom ucr:userChattingRooms) {
            if( ucr.getUser().getId().equals(user.getId())) {//해당 유저의 해당 방 입장시간을 가저옴
                enterTime = ucr.getEnterTime();
                break;
            }
        }

        //roomId에 해당하는 메세지 가저오기
        List<Message> messages = messageService.findMessages(roomId);
        List<Message> filteredMessages = messageService.filterMessage(messages, enterTime);

        List<GetMessageDTO> messageDTOs = new ArrayList<>();
        for (Message m: filteredMessages) {
            GetMessageDTO getMessageDTO = new GetMessageDTO();
            getMessageDTO.setChattingRoom(m.getChattingRoom());
            getMessageDTO.setId(m.getId());
            if(m.getUser() != null){ //나감, 들어옴 메세지일 경우
                getMessageDTO.setSenderId(m.getUser().getId());
                getMessageDTO.setSendName(m.getUser().getName());
            }
            getMessageDTO.setSendTime(m.getSendTime());
            getMessageDTO.setTextMsg(m.getTextMsg());
            messageDTOs.add(getMessageDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(messageDTOs);
    }
}

