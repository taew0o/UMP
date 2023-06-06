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
import ppkjch.ump.dto.EvaluateAppointmentDTO;
import ppkjch.ump.dto.EvaluationInfo;
import ppkjch.ump.dto.InviteRoomDTO;
import ppkjch.ump.dto.MakeAppointmentRoomDTO;
import ppkjch.ump.entity.AppointmentChattingRoom;
import ppkjch.ump.entity.ChattingRoom;
import ppkjch.ump.entity.User;
import ppkjch.ump.entity.UserChattingRoom;
import ppkjch.ump.exception.RoomFullException;
import ppkjch.ump.service.AppointmentService;
import ppkjch.ump.service.ChattingRoomService;
import ppkjch.ump.service.UserService;

import java.util.*;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final UserService userService;
    private final ChattingRoomService chattingRoomService;

    @PostMapping("/appointment-room")
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
            Long chattingRoomId = appointmentService.saveAppointment(users, roomInfo.getRoomName(), roomInfo.getCreateTime(), roomInfo.getTime(), roomInfo.getLocation());
            return new ResponseEntity<Long>(chattingRoomId, HttpStatus.OK);
        }
        catch (RoomFullException e ){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/appointment") //약속 정보를 불러올 때 필요
    public ResponseEntity<List<AppointmentChattingRoom>> getAppointment(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        String userId = (String)session.getAttribute("userId");
        User user = userService.findUser(userId);

        List<AppointmentChattingRoom> appointmentTimes = appointmentService.findAppointments(user);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentTimes);
    }

    @PostMapping("/appointment-room/member")
    public ResponseEntity<String> inviteAptChattingRoom(@RequestBody InviteRoomDTO inviteDTO) {
        //유저 ID정보로 채팅방에 user정보 추가하고 추가된 방을 반환
        System.out.println("inviteDTO.getEnterTime() = " + inviteDTO.getEnterTime());
        try {
            List<User> invitees = new ArrayList<>();
            for (String id : inviteDTO.getInviteeIds()) {
                User invitee = userService.findUser(id);
                invitees.add(invitee);
            }
            ChattingRoom chattingRoom = appointmentService.findAppointmentChattingRoom(inviteDTO.getRoomId());
            chattingRoomService.inviteRoom(chattingRoom, invitees, inviteDTO.getEnterTime());

        } catch (RoomFullException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("성공적으로 초대");
    }
    @PostMapping("/appointment-score")
    public ResponseEntity<?> evaluateAppointment(HttpServletRequest request, @RequestBody EvaluateAppointmentDTO evaluateAppointmentDTO){
        /**
         *  평가 더하는 로직
         */
        //방 조회
        Long roomId = Long.parseLong(evaluateAppointmentDTO.getRoomId());
        AppointmentChattingRoom room = appointmentService.findAppointmentChattingRoom(roomId);
        //유저 및 유저 채팅룸 조회하여 해당 유저 결과 계산
        for (EvaluationInfo e : evaluateAppointmentDTO.getEvaluationInfoList()) {
            User user = userService.findUser(e.getUserId());
            UserChattingRoom userChattingRoom = chattingRoomService.findUserChattingRoom(user,room);
            userChattingRoom.sumScore(e.getNumAttend(), e.getNumNotAttend(), e.getNumLate());
        }
        /**
         *  방 나가는 로직
         */
        // 세션에서 유저 ID 가져오기
        HttpSession session = request.getSession(false);
        String userId = (String)session.getAttribute("userId");
        // 유저 ID를 사용하여 유저 정보 조회
        User user = userService.findUser(userId);

        appointmentService.goOutRoom(user,room);

        return ResponseEntity.status(HttpStatus.OK).body("평가 정보가 입력 되었습니다");
    }

}
