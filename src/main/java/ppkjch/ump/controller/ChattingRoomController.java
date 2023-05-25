package ppkjch.ump.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ppkjch.ump.dto.UserAndRoomDTO;
import ppkjch.ump.entity.ChattingRoom;
import ppkjch.ump.service.ChattingRoomService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ChattingRoomController {
    private final ChattingRoomService chattingRoomService;

    @PostMapping("/chattingroom")
    public Long makeChattingRoom(@RequestBody List<String> userIds){
        // 유저 IDs 정보로 채팅방을 만들고 만들어진 채팅방 ID를 반환
        return chattingRoomService.makeRoom(userIds);
    }

    @GetMapping("/chattingrooms")
    public ResponseEntity<String> getChattingRooms(@CookieValue(name = "userId") String userId){
        //userId로 UserChattingRoom에 매핑된 모든 방정보를 찾아 반환
        return null;
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

    @DeleteMapping("chattingroom/member")
    public ResponseEntity<String> goOutChattingRoom(@CookieValue String userId, @RequestBody Long roomId){
        //유저 ID정보와 RoomId정보로 UserChattingRoom에서 찾아 삭제
        return null;
    }


//    @ExceptionHandler(RoomFullException.class)
//    public ResponseEntity<ErrorResponse> handleCustomException(RoomFullException ex) {
//        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
}
