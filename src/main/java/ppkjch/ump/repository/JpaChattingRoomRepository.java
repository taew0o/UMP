package ppkjch.ump.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ppkjch.ump.entity.ChattingRoom;
import ppkjch.ump.entity.User;
import ppkjch.ump.entity.UserChattingRoom;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaChattingRoomRepository {
    private final EntityManager em;

    public void save(ChattingRoom chattingRoom){
        em.persist(chattingRoom);
    }

    public ChattingRoom findOne(Long id){
        return em.find(ChattingRoom.class, id);
    }

    public List<ChattingRoom> findAll() {
        return em.createQuery("select cr from ChattingRoom cr", ChattingRoom.class)
                .getResultList();
    }

    //채팅룸 - 유저 관계 List를 전체 조회할 필요가 있는지?
    public List<UserChattingRoom> findChatRoomByUser(User user){
        return em.createQuery( "select ucr from UserChattingRoom ucr where ucr.user = :user", UserChattingRoom.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<ChattingRoom> findChattingRoomByUser(User user){
        return em.createQuery( "select ucr.chattingRoom from UserChattingRoom ucr where ucr.user = :user", ChattingRoom.class)
                .setParameter("user", user)
                .getResultList();
    }
    public void goOutRoom(User user, ChattingRoom cr){
        em.createQuery("delete from UserChattingRoom ucr where ucr.user =:user and ucr.chattingRoom =:cr")
                .setParameter("user",user)
                .setParameter("cr",cr)
                .executeUpdate();
    }
    public void removeRoom(ChattingRoom chattingRoom){
        em.remove(chattingRoom);
    }
}