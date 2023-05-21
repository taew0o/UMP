package ppkjch.ump.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ppkjch.ump.entity.Message;
import ppkjch.ump.repository.JpaMessageRepository;

@Service
@RequiredArgsConstructor
public class MessageService {

    final private JpaMessageRepository jpaMessageRepository;

    @Transactional
    public Long send(Message message){ //DB로 보내고 MessageID반환
        jpaMessageRepository.save(message);
        return message.getId();
    }

    public Message findMessage(Long id){
        return jpaMessageRepository.findOne(id);
    }
}
