package ppkjch.ump.controller;



import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ppkjch.ump.dto.ChangeUserDTO;
import ppkjch.ump.dto.FriendRequestDTO;
import ppkjch.ump.dto.LoginForm;
import ppkjch.ump.entity.User;
import ppkjch.ump.dto.SignupForm;
import ppkjch.ump.exception.FriendExistException;
import ppkjch.ump.exception.FriendRequestExistException;
import ppkjch.ump.exception.NotValidUserId;
import ppkjch.ump.service.FriendService;
import ppkjch.ump.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;
    private final FriendService friendService;

    //회원가입 처리 메서드
    @PostMapping("/signup")
    @ResponseBody
    public User signup(@RequestBody SignupForm signupForm) {
        System.out.printf(signupForm.toString());

        User user = new User(); //유저 새로 만들어 form정보 받아 저장
        user.setId(signupForm.getId());
        user.setName(signupForm.getName());
        user.setPassword(signupForm.getPassword());
        user.setPhone_num(signupForm.getPhone_num());
        userService.join(user);
        return user;
    }

    //로그인 처리 메서드
    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginForm loginForm) {

        try {
            User validUser = userService.checkLoginException(loginForm.getId(), loginForm.getPassword());
            HttpSession session = request.getSession();
            session.setAttribute("userId", loginForm.getId());
            Cookie cookie = new Cookie("sessionId", session.getId());
            cookie.setPath("/**");
            cookie.setMaxAge(60 * 60 * 24); // 쿠키의 유효 시간 설정 (초 단위)

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, session.getId());
            String responseBody = "로그인 성공";


            return ResponseEntity.ok().headers(headers).body(responseBody);
        } catch (RuntimeException r) {
            return new ResponseEntity<>(r.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/user")
    public ResponseEntity<User> getUserInfo(HttpServletRequest request){
        // 세션에서 유저 ID 가져오기
        HttpSession session = request.getSession(false);
        String userId = (String)session.getAttribute("userId");
        // 유저 ID를 사용하여 유저 정보 조회
        User user = userService.findUser(userId);

        return ResponseEntity.ok(user);
    }

    @PutMapping("/user")
    public ResponseEntity<User> getUserInfo(HttpServletRequest request, @RequestBody ChangeUserDTO changeUserDTO){
        // 세션에서 유저 ID 가져오기
        HttpSession session = request.getSession(false);
        String userId = (String)session.getAttribute("userId");
        // 유저 ID를 사용하여 유저 정보 조회
        User user = userService.findUser(userId);
        // DTO 정보 이용하여 정보 수정
        userService.updateUser(user, changeUserDTO.getName(), changeUserDTO.getPhone_num(), changeUserDTO.getPassword());

        return ResponseEntity.ok(user);
    }
    @GetMapping("/friends")
    public ResponseEntity<List<User>> getFriends(HttpServletRequest request) {
        // 세션에서 유저 ID 가져오기
        HttpSession session = request.getSession(false);
        String userId = (String)session.getAttribute("userId");
        // 유저 ID를 사용하여 유저 정보 조회
        User user = userService.findUser(userId);
        List<User> friends = userService.findFriend(user);

        return ResponseEntity.status(HttpStatus.OK).body(friends);
    }


    @PostMapping("/friend-request")
    public ResponseEntity<?> requestFriend(HttpServletRequest request, @RequestBody FriendRequestDTO friendRequestDTO){
        // 세션에서 유저 ID 가져오기
        HttpSession session = request.getSession(false);
        String userId = (String)session.getAttribute("userId");
        // 유저 ID를 사용하여 유저 정보 조회
        String friendId = friendRequestDTO.getFriendId();
        try {
            userService.checkMyself(userId, friendId);
            User user1 = userService.findUser(userId);
            User user2 = userService.findUser(friendId);
            friendService.request(user1, user2);
            return ResponseEntity.ok(user2);
        }
        catch (FriendExistException | FriendRequestExistException | NotValidUserId e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/friend-requests")
    public ResponseEntity<List<?>> getFriendRequest(HttpServletRequest request){ //Request로 제네릭 타입 추후 수정
        // 세션에서 유저 ID 가져오기
        HttpSession session = request.getSession(false);
        String userId = (String)session.getAttribute("userId");
        // 유저 ID를 사용하여 유저 정보 조회
        User user = userService.findUser(userId);
        List<User> friendRequestList = friendService.findFriendRequestList(user);
        return ResponseEntity.ok(friendRequestList);
    }

//    @PostMapping("friend-reponse")
//    public ResponseEntity<List<?>> getFriendRequest(HttpServletRequest request){ //Request로 제네릭 타입 추후 수정
//        // 세션에서 유저 ID 가져오기
//        HttpSession session = request.getSession(false);
//        String userId = (String)session.getAttribute("userId");
//        // 유저 ID를 사용하여 유저 정보 조회
//        User user = userService.findUser(userId);
//        List<User> friendRequestList = friendService.findFriendRequestList(user);
//        return ResponseEntity.ok(friendRequestList);
//    }
}