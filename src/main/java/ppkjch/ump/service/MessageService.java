package ppkjch.ump.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ppkjch.ump.entity.ChattingRoom;
import ppkjch.ump.entity.Message;
import ppkjch.ump.entity.UserChattingRoom;
import ppkjch.ump.repository.JpaChattingRoomRepository;
import ppkjch.ump.repository.JpaMessageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    final private JpaMessageRepository jpaMessageRepository;
    final private JpaChattingRoomRepository jpaChattingRoomRepository;
    @Transactional
    public Long send(Message message){ //DB로 보내고 MessageID반환
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

}
