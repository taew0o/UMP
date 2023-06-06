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

    public void updateAppointmentScore(int maxScore){
        this.getAppointmentScore().applyEvaluate(maxScore);
    }

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

    public void applyEvaluate(int maxScore){
        if(maxScore == 1){
            this.numAttend += 1;
        }
        else if (maxScore == -1) {
            this.numNotAttend += 1;
        }
        else if (maxScore == 0){
            this.numLate += 1;
        }
    }
}