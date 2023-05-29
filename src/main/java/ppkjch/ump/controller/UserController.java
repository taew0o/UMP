package ppkjch.ump.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ppkjch.ump.dto.LoginForm;
import ppkjch.ump.entity.User;
import ppkjch.ump.dto.SignupForm;
import ppkjch.ump.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    //회원가입 처리 메서드
    @PostMapping("/signup")
    @ResponseBody
    public User signup(@RequestBody SignupForm signupForm) {
        System.out.printf(signupForm.toString());

        User user = new User(); //유저 새로 만들어 form정보 받아 저장
        user.setId(signupForm.getId());
        user.setName(signupForm.getName());
        user.setPassword(signupForm.getPassword());

        userService.join(user);

        return user;
    }

    //로그인 처리 메서드
    @PostMapping("/login")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginForm loginForm) {
        HttpSession session = request.getSession();
        //로그인 검사
        try {
            session.setAttribute("loginUser", loginForm.getId());
            Cookie cookie = new Cookie("sessionId", session.getId());
            cookie.setMaxAge(60 * 60 * 24); // 쿠키의 유효 시간 설정 (초 단위)
            response.addCookie(cookie);

            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (RuntimeException r) {
            return new ResponseEntity<>(r.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("friends")
    public ResponseEntity<List<User>> getFriends(@CookieValue String userId) {
        User findUser = userService.findUser(userId);
        List<User> friends = userService.findFriend(findUser);

        return ResponseEntity.status(HttpStatus.OK).body(friends);
    }

    @GetMapping("user")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request){
        // 세션에서 유저 ID 가져오기
        HttpSession session = request.getSession(false);
        String userId = (String)session.getAttribute("userId");

        // 유저 ID를 사용하여 유저 정보 조회
        User user = userService.findUser(userId);

        return ResponseEntity.ok(user);
    }

    @PostMapping("friend-request")
    public ResponseEntity<?> requestFriend(@CookieValue String userId, @RequestBody String friendId){
        User user1 = userService.findUser(userId);
        User user2 = userService.findUser(friendId);
        //request(user1, user2)
        return ResponseEntity.status(HttpStatus.OK).build();
    }

//    @GetMapping("friend-request")
//    public ResponseEntity<List<?>> getFriendRequest(@CookieValue String sessionId){ //Request로 제네릭 타입 추후 수정
//
//        userService.findUser(userId);
//
//        ResponseEntity.
//    }
}