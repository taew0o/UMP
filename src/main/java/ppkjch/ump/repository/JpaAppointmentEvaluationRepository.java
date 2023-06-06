package ppkjch.ump.repository;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ppkjch.ump.entity.*;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaAppointmentEvaluationRepository {

    private final EntityManager em;

    public void save(AppointmentEvaluation ae){
        em.persist(ae);
    }

    public AppointmentEvaluation findAppointmentEvaluationByRoomAndUser(AppointmentChattingRoom acr, User u){
        return em.createQuery( "select ae from AppointmentEvaluation ae where ae.appointmentchattingRoom = :acr and ae.user = :u", AppointmentEvaluation.class)
                .setParameter("acr", acr)
                .setParameter("u", u)
                .getSingleResult();
    }
    public List<AppointmentEvaluation> findAppointmentEvaluationByRoom(AppointmentChattingRoom acr){
        return em.createQuery( "select ae from AppointmentEvaluation ae where ae.appointmentchattingRoom = :acr", AppointmentEvaluation.class)
                .setParameter("acr", acr)
                .getResultList();
    }
    public void removeAppointmentEvaluation(AppointmentEvaluation ae){
        em.remove(ae);
    }

    public void removeAppointmentEvaluations(AppointmentChattingRoom acr){
         em.createQuery( "delete from AppointmentEvaluation ae where ae.appointmentchattingRoom = :acr")
                .setParameter("acr", acr)
                 .executeUpdate();
    }


}
