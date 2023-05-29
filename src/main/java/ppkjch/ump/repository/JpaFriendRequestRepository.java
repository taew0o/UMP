package ppkjch.ump.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ppkjch.ump.entity.Friend;
import ppkjch.ump.entity.FriendRequest;
import ppkjch.ump.entity.User;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaFriendRequestRepository {
    private final EntityManager em;

    public List<FriendRequest> findAllFriendRequest(){
        return em.createQuery("select fr from FriendRequest fr", FriendRequest.class)
                .getResultList();
    }
    //자신에게 친구를 요청한 사람들의 리스트 찾기
    public List<User> findSender(User user){
        List<User> result = new ArrayList<>();
        result.addAll(em.createQuery("select f.sender from FriendRequest f where f.receiver = :user", User.class)
                .setParameter("user",user)
                .getResultList());
        return result;
    }
    public void save(FriendRequest friendRequest){
        em.persist(friendRequest);
    }
    public FriendRequest findOne(Long id){
        return em.find(FriendRequest.class, id);
    }
}
