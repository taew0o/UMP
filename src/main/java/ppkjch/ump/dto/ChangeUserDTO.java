package ppkjch.ump.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeUserDTO {
    private String password;
    private String name;
    private String phone_num;
}
