package ppkjch.ump.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ppkjch.ump.entity.LoginForm;
import ppkjch.ump.entity.Member;
import ppkjch.ump.entity.SignupForm;
import ppkjch.ump.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    //회원가입 처리 메서드
    @PostMapping("/signup")
    public String signup(@ModelAttribute("SignupForm") SignupForm signupForm){
        Member member = new Member();
        member.setName(signupForm.getName());
        member.setPassword(signupForm.getPassword());

        memberService.join(member);

        return "redirect:/";
    }
    //로그인 처리 메서드
    @PostMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("loginForm") LoginForm loginForm) {
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