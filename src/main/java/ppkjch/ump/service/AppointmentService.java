package ppkjch.ump.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import ppkjch.ump.repository.JpaAppointmentChattingRoomRepository;

@RequiredArgsConstructor
@Service
public class AppointmentService {
    private final JpaAppointmentChattingRoomRepository jpaAppointmentChattingRoomRepository;

}
