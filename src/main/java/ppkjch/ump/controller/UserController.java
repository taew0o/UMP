package ppkjch.ump.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ppkjch.ump.dto.LoginForm;
import ppkjch.ump.entity.User;
import ppkjch.ump.dto.SignupForm;
import ppkjch.ump.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    //회원가입 처리 메서드
    @PostMapping("/signup")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000")
    public User signup(@RequestBody SignupForm signupForm){
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
    @CrossOrigin(origins = "http://localhost:3000")
    public String login(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginForm loginForm) {
        HttpSession session = request.getSession();
        //로그인 검사
        if (true) {
            session.setAttribute("loginUser", loginForm.getId());
            Cookie cookie = new Cookie("sessionId", session.getId());
            cookie.setMaxAge(60 * 60 * 24); // 쿠키의 유효 시간 설정 (초 단위)
            response.addCookie(cookie);
            return "redirect:/main";
        } else {
            return "redirect:/loginForm";
        }
    }
}