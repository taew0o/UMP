package ppkjch.ump.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
        // 만들어진 채팅방 ID를 반환
        return chattingRoomService.makeRoom(userIds);
    }

    @PutMapping("/chattingroom")
    public Long inviteChattingRoom(@RequestBody List<String> userIds){
        // 만들어진 채팅방 ID를 반환
        //return chattingRoomService.invite(userIds);
        return null;
    }
}
