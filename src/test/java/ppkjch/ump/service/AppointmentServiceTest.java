package ppkjch.ump.service;

import org.junit.jupiter.api.Test;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ppkjch.ump.dto.EvaluateAppointmentDTO;
import ppkjch.ump.entity.*;
import ppkjch.ump.exception.RoomFullException;
import ppkjch.ump.repository.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentServiceTest {

    @Test
    void saveAppointment() {
        User U = new User();
        User M = new User();
        User P = new User();
    }

    @Test
    void findAppointments() {
        User U = new User();
        User M = new User();
        User P = new User();
    }

    @Test
    void findAppointmentChattingRoom() {
        User U = new User();
        User M = new User();
        User P = new User();
    }

    @Test
    void saveAppointmentEvaluation() {
        User U = new User();
        User M = new User();
        User P = new User();
    }

    @Test
    void findUserByAppointmentChattingRoom() {
        User U = new User();
        User M = new User();
        User P = new User();
    }
}