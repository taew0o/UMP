package ppkjch.ump.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 세션 정보 검증 로직을 구현

        Cookie[] cookies = request.getCookies();
        String sessionId = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("sessionId".equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }

        if (sessionId == null) {
            throw new Exception("세션 정보가 없음");
        }

        HttpSession session = request.getSession(false);
        System.out.println(session.isNew());

        // 세션 정보가 없으면 로그인 페이지로 리다이렉트
        if (session == null || session.getAttribute("JSESSIONID") == null) {
            throw new Exception("세션 정보가 없음");
        }

        // 세션 정보가 유효한지 검증하는 추가 로직

        return true;
    }
}
