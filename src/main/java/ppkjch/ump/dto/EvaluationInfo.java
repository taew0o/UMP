package ppkjch.ump.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EvaluationInfo {
    private int numAttend;
    private int numNotAttend;
    private int numLate;
    private String userId;
}
