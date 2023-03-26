package ppkjch.ump;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ppkjch.ump.entity.Member;
import ppkjch.ump.repository.JpaMemberRepository;
import ppkjch.ump.service.MemberService;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)

public class MemberEntityTest {

    @Autowired
    public JpaMemberRepository jpaMemberRepository;
    @Autowired
    public MemberService memberService;

    @Test
    @Rollback(value = true)
    public void 회원_엔티티(){
        Member member = new Member();

        member.setName("wuseong");
        memberService.join(member);
    }
}
