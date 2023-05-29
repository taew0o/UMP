package ppkjch.ump.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ppkjch.ump.entity.Friend;
import ppkjch.ump.entity.User;
import ppkjch.ump.repository.JpaFriendRepository;
import ppkjch.ump.repository.JpaUserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final JpaUserRepository jpaUserRepository;

    private final JpaFriendRepository jpaFriendRepository;


    public List<User> findFriendList(String user_id){
//        List<Friend> all_friend = jpaFriendRepository.findAllFriend();
//        List<User> friend_list = new ArrayList<>();
//        User user = jpaUserRepository.findOne(user_id);
//        for(int i = 0 ; i < all_friend.size() ; i++){
//            if(all_friend.get(i).getUser1().equals(user)){
//                friend_list.add(all_friend.get(i).getUser2());
//            }
//            else if(all_friend.get(i).getUser2().equals(user)){
//                friend_list.add(all_friend.get(i).getUser1());
//            }
//        }
        User user = jpaUserRepository.findOne(user_id);
        return jpaFriendRepository.findFriend(user);
    }

    public void addFriend(User u1, User u2){
        Friend friend = new Friend();
        friend.setUser1(u1);
        friend.setUser2(u2);
        jpaFriendRepository.save(friend);
    }

    public void removeFriend(User u1, User u2){

    }

}
