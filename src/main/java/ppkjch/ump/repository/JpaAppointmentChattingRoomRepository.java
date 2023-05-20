package ppkjch.ump.repository;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ppkjch.ump.entity.AppointmentChattingRoom;
import ppkjch.ump.entity.ChattingRoom;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaAppointmentChattingRoomRepository {
    private final EntityManager em;

    public void save(AppointmentChattingRoom appointmentchattingroom){
        em.persist(appointmentchattingroom);
    }

    public AppointmentChattingRoom findOne(Long id){
        return em.find(AppointmentChattingRoom.class, id);
    }

    public List<AppointmentChattingRoom> findAll() {
        return em.createQuery("select apcr from AppointmentChattingRoom  apcr", AppointmentChattingRoom.class)
                .getResultList();
    }
}
