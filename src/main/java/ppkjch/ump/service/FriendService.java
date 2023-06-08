package ppkjch.ump.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ppkjch.ump.entity.Friend;
import ppkjch.ump.entity.FriendRequest;
import ppkjch.ump.entity.User;
import ppkjch.ump.exception.FriendExistException;
import ppkjch.ump.exception.FriendNotExistException;
import ppkjch.ump.exception.FriendRequestExistException;
import ppkjch.ump.repository.JpaFriendRepository;
import ppkjch.ump.repository.JpaFriendRequestRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final JpaFriendRepository jpaFriendRepository;

    private final JpaFriendRequestRepository jpaFriendRequestRepository;

    public List<User> findFriendList(User user){
        return jpaFriendRepository.findFriendList(user);
    }

    @Transactional
    public void request(User sender, User receiver){
        if (jpaFriendRequestRepository.findSender(receiver).contains(sender)) {
            throw new FriendRequestExistException("이미 해당 유저에게 친구 요청을 하였습니다.");
        } else if (jpaFriendRepository.findFriend(sender, receiver) != null) {
            throw new FriendExistException("이미 친구로 등록이 되어 있는 유저입니다.");
        }
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);
        jpaFriendRequestRepository.save(friendRequest);


    }

    public List<User> findFriendRequestList(User receiver){
        return jpaFriendRequestRepository.findSender(receiver);
    }

    @Transactional
    public void takeRequest(User sender, User receiver, Boolean is_accept){
        if(is_accept){
            addFriend(sender,receiver);
        }
        deleteRequest(sender,receiver);
    }
    @Transactional
    public void addFriend(User u1, User u2){
        Friend friend = new Friend();
        friend.setUser1(u1);
        friend.setUser2(u2);
        jpaFriendRepository.save(friend);
    }
    @Transactional
    public void deleteRequest(User sender, User receiver){
        jpaFriendRequestRepository.deleteRequest(sender,receiver);
    }

    @Transactional
    public void removeFriend(User u1, User u2){
        if(jpaFriendRepository.findFriend(u1,u2) == null){
            throw new FriendNotExistException("삭제하려는 친구가 존재하지 않습니다");
        }
        else{
            jpaFriendRepository.delete(u1,u2);
        }
    }

}
