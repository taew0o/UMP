package ppkjch.ump.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ppkjch.ump.entity.Friend;
import ppkjch.ump.entity.User;
import ppkjch.ump.entity.UserChattingRoom;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor

public class JpaFriendRepository {
    private final EntityManager em;
    public List<Friend> findAllFriend(){
        return em.createQuery("select f from Friend f", Friend.class)
                .getResultList();
    }
    public void save(Friend friend){
        em.persist(friend);
    }

    public List<Friend> findByUser(User user){
        return em.createQuery( "select f from Friend where f. = :user", UserChattingRoom.class)
                .setParameter("user", user)
                .getResultList();
    }

}
