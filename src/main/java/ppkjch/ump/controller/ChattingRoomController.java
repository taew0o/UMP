package ppkjch.ump.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ppkjch.ump.service.ChattingRoomService;

@Controller
@RequiredArgsConstructor
public class ChattingRoomController {
    private final ChattingRoomService chattingRoomService;
}
