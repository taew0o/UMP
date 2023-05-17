package ppkjch.ump;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupForm {
    private String id;
    private String name;
    private String password;

    @Override
    public String toString() {
        return "SignupForm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
