package ppkjch.ump.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ppkjch.ump.entity.ChattingRoom;
import ppkjch.ump.entity.Message;
import ppkjch.ump.entity.User;
import ppkjch.ump.entity.UserChattingRoom;

import java.util.List;

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

    public List<Message> findMessageByRoom(ChattingRoom chattingRoom){
        return em.createQuery( "select m from Message m where m.chattingRoom = :chattingRoom", Message.class)
                .setParameter("chattingRoom", chattingRoom)
                .getResultList();
    }

    public void removeMessage(Message message){
        em.remove(message);
    }
}
