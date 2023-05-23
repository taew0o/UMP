package ppkjch.ump.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ppkjch.ump.entity.Friend;
import ppkjch.ump.entity.User;
import ppkjch.ump.repository.JpaFriendRepository;
import ppkjch.ump.repository.JpaUserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JpaUserRepository jpaUserRepository;
    private final JpaFriendRepository jpaFriendRepository;

    @Transactional
    public String join(User user){
            jpaUserRepository.save(user);
        return user.getId();
    }

    @Transactional
    public Long makeFriend(Friend friend){
        jpaFriendRepository.save(friend);
        return friend.getId();
    }

    public User findUser(String userId){
        return jpaUserRepository.findOne(userId);
    }
}
