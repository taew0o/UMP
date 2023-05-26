package ppkjch.ump.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


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

    @Embedded
    private AppointmentScore appointmentScore;

}

@Embeddable
class AppointmentScore{
    private int numAttend;
    private int numNotAttend;
    private int numLate;

}
