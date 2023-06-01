package ppkjch.ump.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User{
    @Id
    @NotNull
    @Column(name = "user_id")
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String password;
    @NotNull
    private String phone_num;

    @Embedded
    private AppointmentScore appointmentScore = new AppointmentScore();

}

@Embeddable
@Getter
@Setter
class AppointmentScore{

    public AppointmentScore() {
    }

    private int numAttend;
    private int numNotAttend;
    private int numLate;

}