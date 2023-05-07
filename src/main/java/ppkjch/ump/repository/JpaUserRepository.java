package ppkjch.ump.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ppkjch.ump.entity.User;

@Repository
@RequiredArgsConstructor
public class JpaUserRepository {
    private final EntityManager em;
    public void save(User user){
        em.persist(user);
    }
    public User findOne(Long id){
        return em.find(User.class, id);
    }
}
