package ppkjch.ump.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ppkjch.ump.dto.UserAndRoomDTO;
import ppkjch.ump.entity.ChattingRoom;
import ppkjch.ump.entity.Message;
import ppkjch.ump.entity.User;
import ppkjch.ump.service.ChattingRoomService;
import ppkjch.ump.service.MessageService;
import ppkjch.ump.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ChattingRoomController {
    private final ChattingRoomService chattingRoomService;
    private final UserService userService;

    private final MessageService messageService;

    @PostMapping("/chattingroom")
    public ResponseEntity<ChattingRoom> makeChattingRoom(@RequestBody List<String> userIds){
        // 유저 IDs 정보로 유저 조회
        ArrayList<User> users = new ArrayList<>();
        for (String userId: userIds) {
            User findUser = userService.findUser(userId);
            users.add(findUser);
        }
        //유저 정보로 채팅방 만들기
        return new ResponseEntity<>(new ChattingRoom(), HttpStatus.OK);
    }

    @GetMapping("/chattingrooms")
    public ResponseEntity<List<ChattingRoom>> getChattingRooms(@CookieValue(name = "userId") String userId){
        //userId로 UserChattingRoom에 매핑된 모든 방정보를 찾아 반환
        User user = userService.findUser(userId);
        //findRoom(User user): List<CattingRoom> : 태우 추가 완. 테스트 필요\
        List<ChattingRoom> chattingRooms = chattingRoomService.findRoom(user);
        return ResponseEntity.status(HttpStatus.OK).body(chattingRooms);
    }

    @PostMapping("/chattingroom/member")
    public ResponseEntity<String> inviteChattingRoom(@RequestBody UserAndRoomDTO inviteDTO){
        //유저 ID정보로 채팅방에 user정보 추가하고 추가된 방을 반환
        try {
            Long roomId = chattingRoomService.inviteRoom(inviteDTO.getRoomId(), inviteDTO.getInviteeId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //HttpHeaders headers = new HttpHeaders();

        return ResponseEntity.status(HttpStatus.CREATED).body("성공적으로 초대");

    }

    @DeleteMapping("/chattingroom/member")
    public ResponseEntity<?> goOutChattingRoom(@CookieValue String userId, @RequestBody Long roomId){
        //유저 ID정보와 RoomId정보로 UserChattingRoom에서 찾아 삭제
        User user = userService.findUser(userId);
        ChattingRoom room = chattingRoomService.findRoom(roomId);
        //goOutRoom(User, ChattingRoom): void - 태우 추가 완. 테스트 필요
        chattingRoomService.goOutRoom(user,room);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/chattingroom/messages")
    public ResponseEntity<List<Message>> getMessages(@RequestBody Long roomId){ //보안 때문에 userId 넣어야 할 수도?
        List<Message> messages = messageService.findMessages(roomId);
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }
//    @ExceptionHandler(RoomFullException.class)
//    public ResponseEntity<ErrorResponse> handleCustomException(RoomFullException ex) {
//        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
}
