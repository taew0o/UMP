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
import ppkjch.ump.dto.RoomIdDTO;
import ppkjch.ump.dto.UserAndRoomDTO;
import ppkjch.ump.entity.ChattingRoom;
import ppkjch.ump.entity.Message;
import ppkjch.ump.entity.User;
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
            Long chattingRoomId = chattingRoomService.makeRoom(users, roomInfo.getRoomName());
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
    public ResponseEntity<String> inviteChattingRoom(@RequestBody UserAndRoomDTO inviteDTO){
        //유저 ID정보로 채팅방에 user정보 추가하고 추가된 방을 반환
        try {
            User invitee = userService.findUser(inviteDTO.getInviteeId());
            ChattingRoom chattingRoom = chattingRoomService.findRoom(inviteDTO.getRoomId());
            chattingRoomService.inviteRoom(chattingRoom, invitee);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //HttpHeaders headers = new HttpHeaders();

        return ResponseEntity.status(HttpStatus.CREATED).body("성공적으로 초대");

    }

    @DeleteMapping("/chattingroom/member")
    public ResponseEntity<?> goOutChattingRoom(HttpServletRequest request, @RequestParam("roomId") Long roomId){
        // 세션에서 유저 ID 가져오기
        System.out.println("roomId... = " + roomId);
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
    public ResponseEntity<List<GetMessageDTO>> getMessages(@RequestParam("roomId") Long roomId){ //보안 때문에 userId 넣어야 할 수도?
        List<Message> messages = messageService.findMessages(roomId);
        List<GetMessageDTO> messageDTOs = new ArrayList<>();
        for (Message m: messages) {
            GetMessageDTO getMessageDTO = new GetMessageDTO();
            getMessageDTO.setChattingRoom(m.getChattingRoom());
            getMessageDTO.setId(m.getId());
            getMessageDTO.setSenderId(m.getUser().getId());
            getMessageDTO.setSendTime(m.getSendTime());
            getMessageDTO.setTextMsg(m.getTextMsg());
            messageDTOs.add(getMessageDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(messageDTOs);
    }
//    @ExceptionHandler(RoomFullException.class)
//    public ResponseEntity<ErrorResponse> handleCustomException(RoomFullException ex) {
//        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
}
