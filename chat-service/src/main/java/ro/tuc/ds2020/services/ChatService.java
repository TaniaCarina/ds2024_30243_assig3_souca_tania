package ro.tuc.ds2020.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.entity.Message;
import ro.tuc.ds2020.repositories.ChatRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Message saveMessage(Message message) {
        System.out.println("SAVED MESSAGE");
        return chatRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return chatRepository.findAll();
    }


}
