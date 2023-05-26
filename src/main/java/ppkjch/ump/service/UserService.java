package ppkjch.ump.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ppkjch.ump.entity.Friend;
import ppkjch.ump.entity.User;
import ppkjch.ump.exception.IdDuplicateException;
import ppkjch.ump.exception.loginFailException;
import ppkjch.ump.exception.passwordNotEqualException;
import ppkjch.ump.repository.JpaFriendRepository;
import ppkjch.ump.repository.JpaUserRepository;

import java.util.ArrayList;
import java.util.List;

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

    public void signUp(String id, String name, String pw, String pw_re){
        if (findUser(id) != null) {
            throw new IdDuplicateException("이미 입력하신 아이디가 존재합니다.");
        }
        else{
            User user = new User();
            user.setId(id);
            user.setName(name);
            user.setPassword(pw);
            jpaUserRepository.save(user);
        }
    }

    public User login(String user_id, String user_pw){
        if(findUser(user_id) == null || !findUser(user_id).getPassword().equals(user_pw)){
            throw new loginFailException("로그인에 실패하였습니다. 아이디와 비밀번호를 다시 한번 확인해주세요.");
        }
        else{
            return findUser(user_id);
        }
    }

    public List<User> findFriend(User user){
        List<Friend> findFriend = jpaFriendRepository.findByUser(user);
        List<User> friendUsers = new ArrayList<>();
        for (Friend f: findFriend) {
            User user1 = f.getUser1();
            User user2 = f.getUser2();
            if(user1.equals(user)){ //유저1이면 유저2추가
                friendUsers.add(user1);
            }
            else{ //유저 2이면 유저 1추가
                friendUsers.add(user2);
            }
        }
        return friendUsers;
    }

}
