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
    public Long addFriend(Friend friend){
        jpaFriendRepository.save(friend);
        return friend.getId();
    }

    public boolean signUp(String id, String name, String pw, String pw_re){
        if (findUser(id) == null) {
            return false;
        }
        else if(!pw.equals(pw_re)){
            return false;
        }
        else{
            User user = new User();
            user.setId(id);
            user.setName(name);
            user.setPassword(pw);
            jpaUserRepository.save(user);
            return true;
        }
    }

    public User findUser(String userId){
        return jpaUserRepository.findOne(userId);
    }
}
