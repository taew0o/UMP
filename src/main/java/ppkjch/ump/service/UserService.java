package ppkjch.ump.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ppkjch.ump.entity.Friend;
import ppkjch.ump.entity.User;
import ppkjch.ump.repository.JpaFriendRepository;
import ppkjch.ump.repository.JpaUserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JpaUserRepository jpaMemberRepository;
    private final JpaFriendRepository jpaFriendRepository;

    public String join(User user){
            jpaMemberRepository.save(user);
        return user.getId();
    }

    public Long addFriend(Friend friend){
        jpaFriendRepository.save(friend);
        return friend.getId();
    }

    public User findMember(Long memberId){
        return jpaMemberRepository.findOne(memberId);
    }
}
