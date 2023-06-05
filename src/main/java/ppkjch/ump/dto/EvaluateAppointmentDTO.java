package ppkjch.ump.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class EvaluateAppointmentDTO {
    private int numAttend;
    private int numNotAttend;
    private int numLate;
    private String userId;
    private String roomId;
}
