package ppkjch.ump.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ppkjch.ump.entity.User;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class JpaUserRepository {
    private final EntityManager em;
    public void save(User user){
        em.persist(user);
    }

    public void update(User user, String name, String phone_num, String password){
        em.createQuery("update User u set u.name = :name where u =:user")
                .setParameter("user",user)
                .setParameter("name", name)
                .executeUpdate();

        em.createQuery("update User u set u.phone_num = :phone_num where u =:user")
                .setParameter("user",user)
                .setParameter("phone_num", phone_num)
                .executeUpdate();

        em.createQuery("update User u set u.password = :password where u =:user")
                .setParameter("user",user)
                .setParameter("password", password)
                .executeUpdate();
    }
    public User findOne(String id){
        return em.find(User.class, id);
    }
}

