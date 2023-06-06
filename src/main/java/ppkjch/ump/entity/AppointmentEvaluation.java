package ppkjch.ump.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

@Entity
@Getter
@Setter
public class AppointmentEvaluation {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name="chattingRoom_id")
    private AppointmentChattingRoom appointmentchattingRoom;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name="user_id")
    private User user;

    private int sumAttend;
    private int sumNotAttend;
    private int sumLate;


    public void sumScore(int sumAttend, int sumNotAttend, int sumLate){
        this.sumAttend += sumAttend;
        this.sumNotAttend += sumNotAttend;
        this.sumLate += sumLate;
    }

    public int getMaxScore(){ //참석은 1, 불참은 -1, 늦참은 0 동률은 2
        if(sumAttend > sumNotAttend){
            if(sumAttend > sumLate){ //참석
                return 1;
            }
            else if(sumAttend < sumLate){ //늦참
                return 0;
            }
            else{ // 참석 늦참 동률
                return 2;
            }
        }
        else if (sumAttend < sumNotAttend){
            if(sumNotAttend > sumLate){
                return -1; //불참
            }
            else if(sumNotAttend < sumLate){
                return 0; //늦참
            }
            else{
                return 2; //늦참 불참 동률
            }
        }
        else{
            return 2; //불참 참삭 동률
        }
    }


}
