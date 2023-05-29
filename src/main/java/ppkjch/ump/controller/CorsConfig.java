package ppkjch.ump.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/user")
                .allowedOrigins("http://localhost:3000") // 허용할 Origin을 설정합니다.
                .allowCredentials(true); // CORS 요청에 대해 인증 정보를 포함하도록 설정합니다.
    }
}
