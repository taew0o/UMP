package ppkjch.ump.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ppkjch.ump.entity.User;
import ppkjch.ump.repository.JpaUserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JpaUserRepository jpaMemberRepository;

    public String join(User user){
        jpaMemberRepository.save(user);
        return user.getId();
    }

    public User findMember(Long memberId){
        return jpaMemberRepository.findOne(memberId);
    }
}
