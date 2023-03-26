package ppkjch.ump.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ppkjch.ump.entity.Member;
import ppkjch.ump.repository.JpaMemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final JpaMemberRepository jpaMemberRepository;

    public Long join(Member member){
        jpaMemberRepository.save(member);
        return member.getId();
    }

    public Member findMember(Long memberId){
        return jpaMemberRepository.findOne(memberId);
    }
}
