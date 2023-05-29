package ppkjch.ump.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ppkjch.ump.entity.Friend;
import ppkjch.ump.entity.FriendRequest;
import ppkjch.ump.entity.User;
import ppkjch.ump.repository.JpaFriendRepository;
import ppkjch.ump.repository.JpaFriendRequestRepository;
import ppkjch.ump.repository.JpaUserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final JpaUserRepository jpaUserRepository;

    private final JpaFriendRepository jpaFriendRepository;

    private final JpaFriendRequestRepository jpaFriendRequestRepository;


    public List<User> findFriendList(String user_id){
        User user = jpaUserRepository.findOne(user_id);
        return jpaFriendRepository.findFriend(user);
    }

    public void request(User sender, User receiver){
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);
        jpaFriendRequestRepository.save(friendRequest);
    }

    public List<User> findFriendRequestList(User receiver){
        return jpaFriendRequestRepository.findSender(receiver);
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
