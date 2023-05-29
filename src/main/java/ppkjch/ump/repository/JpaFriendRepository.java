package ppkjch.ump.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ppkjch.ump.entity.Friend;
import ppkjch.ump.entity.User;

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
    public List<User> findFriend(User user){
        List<User> result = new ArrayList<>();
        result.addAll(em.createQuery("select f.user2 from Friend f where f.user1 = :user", User.class)
                .setParameter("user",user)
                .getResultList());
        result.addAll(em.createQuery("select f.user1 from Friend f where f.user2 = :user", User.class)
                .setParameter("user",user)
                .getResultList());
        return result;
    }
    public void save(Friend friend){
        em.persist(friend);
    }
    public Friend findOne(Long id){
        return em.find(Friend.class, id);
    }

}
