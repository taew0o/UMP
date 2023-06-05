package ppkjch.ump.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ppkjch.ump.dto.MakeAppointmentRoomDTO;
import ppkjch.ump.dto.MakeRoomDTO;
import ppkjch.ump.entity.User;
import ppkjch.ump.exception.RoomFullException;
import ppkjch.ump.service.AppointmentService;
import ppkjch.ump.service.ChattingRoomService;
import ppkjch.ump.service.UserService;

import java.util.ArrayList;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final UserService userService;
    private final ChattingRoomService chattingRoomService;

    @PostMapping("/appointmentroom")
    public ResponseEntity<?> makeChattingRoom(HttpServletRequest request, @RequestBody MakeAppointmentRoomDTO roomInfo){
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
            Long chattingRoomId = appointmentService.saveAppointment(users, roomInfo.getRoomName(), roomInfo.getCreateTime(), roomInfo.getDate(), roomInfo.getTime(), roomInfo.getLocation());
            return new ResponseEntity<Long>(chattingRoomId, HttpStatus.OK);
        }
        catch (RoomFullException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/appintment")
//    public ResponseEntity<?> getAppointment()
}
