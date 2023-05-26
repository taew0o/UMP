package ppkjch.ump.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ppkjch.ump.service.AppointmentService;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    //@PostMapping("/appointment-chattingroom")
    //public ResponseEntity<?> makeAppointmentRoom(@RequestBody Long chatt)
}
