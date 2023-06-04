package ppkjch.ump.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ppkjch.ump.entity.ChattingRoom;
import ppkjch.ump.entity.Message;
import ppkjch.ump.entity.User;
import ppkjch.ump.entity.UserChattingRoom;
import ppkjch.ump.repository.JpaChattingRoomRepository;
import ppkjch.ump.repository.JpaMessageRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    final private JpaMessageRepository jpaMessageRepository;
    final private JpaChattingRoomRepository jpaChattingRoomRepository;

    @Transactional
    public Long createMessage(String text, User user, ChattingRoom chattingRoom, Long sendTime){
        Message message = Message.createMessage(text,user,chattingRoom, sendTime);
        jpaMessageRepository.save(message);
        return message.getId();
    }
    @Transactional
    public Long sendMessage(Message message){
        jpaMessageRepository.save(message);
        return message.getId();
    }

    public List<Message> findMessages(Long roomId){
        ChattingRoom room = jpaChattingRoomRepository.findOne(roomId);
        return jpaMessageRepository.findMessageByRoom(room);
    }
    public Message findMessage(Long id){
        return jpaMessageRepository.findOne(id);
    }

    public List<Message> filterMessage(List<Message> messages, Long enterTime) {
        ArrayList<Message> filteredMessages = new ArrayList<>();
        for (Message m: messages) {
            if(m.getSendTime() > enterTime){ // 메세지를 보낸 시간이 입장시간보다 나중인 것만 필터
                filteredMessages.add(m);
            }
        }
        return filteredMessages;
    }
}
