package ppkjch.ump.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ppkjch.ump.entity.Message;
import ppkjch.ump.entity.User;

@Repository
@RequiredArgsConstructor
@Transactional
public class JpaMessageRepository {

    private final EntityManager em;

    public void save(Message message){
        em.persist(message);
    }

    public Message findOne(Long id) {
        return em.find(Message.class, id);
    }
}
