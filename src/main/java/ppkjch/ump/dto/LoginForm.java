package ppkjch.ump.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {
    private Long id;
    private String name;
    private String password;
}
