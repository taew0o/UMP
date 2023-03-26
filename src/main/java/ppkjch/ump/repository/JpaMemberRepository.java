package ppkjch.ump.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ppkjch.ump.entity.Member;

@Repository
@RequiredArgsConstructor
public class JpaMemberRepository {
    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }
    public Member findOne(Long id){
        return em.find(Member.class, id);
    }
}
