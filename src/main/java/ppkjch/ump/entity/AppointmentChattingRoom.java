package ppkjch.ump.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class AppointmentChattingRoom extends ChattingRoom{
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime apt_time;
}
