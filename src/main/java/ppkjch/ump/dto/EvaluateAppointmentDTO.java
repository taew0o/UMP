package ppkjch.ump.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class EvaluateAppointmentDTO {
    private List<EvaluationInfo> evaluationInfoList = new ArrayList<>();
    private String roomId;
}

