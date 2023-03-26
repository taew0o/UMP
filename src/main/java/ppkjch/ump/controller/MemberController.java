package ppkjch.ump.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ppkjch.ump.entity.MemberForm;

@Controller
public class MemberController {


    //로그인 처리 메서드
    @PostMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("memberForm")MemberForm memberForm) {
        HttpSession session = request.getSession();
        //로그인 검사
        if (true) {
            session.setAttribute("loginUser", memberForm.getId());
            Cookie cookie = new Cookie("sessionId", session.getId());
            cookie.setMaxAge(60 * 60 * 24); // 쿠키의 유효 시간 설정 (초 단위)
            response.addCookie(cookie);
            return "redirect:/main";
        } else {
            return "redirect:/loginForm";
        }
    }
}