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
    public List<User> findFriendList(User user){
        List<User> result = new ArrayList<>();
        result.addAll(em.createQuery("select f.user2 from Friend f where f.user1 = :user", User.class)
                .setParameter("user",user)
                .getResultList());
        result.addAll(em.createQuery("select f.user1 from Friend f where f.user2 = :user", User.class)
                .setParameter("user",user)
                .getResultList());
        return result;
    }

    public Friend findFriend(User u1, User u2){
        Friend friend1 = em.createQuery("select f from Friend f where f.user1 = :u1 and f.user2 = :u2", Friend.class)
                .getSingleResult();
        Friend friend2 = em.createQuery("select f from Friend f where f.user1 = :u2 and f.user2 = :u1", Friend.class)
                .getSingleResult();
        if(friend1 != null){
            return friend1;
        }
        else return friend2;
    }
    public void save(Friend friend){
        em.persist(friend);
    }
    public void delete(User u1, User u2){
        em.createQuery("delete from Friend f where f.user1 = :u1 and f.user2 = :u2")
                .setParameter("u1",u1)
                .setParameter("u2",u2)
                .executeUpdate();

        em.createQuery("delete from Friend f where f.user1 = :u2 and f.user2 = :u1")
                .setParameter("u1",u1)
                .setParameter("u2",u2)
                .executeUpdate();
    }
    public Friend findOne(Long id){
        return em.find(Friend.class, id);
    }

}
