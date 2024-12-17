package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.entity.Message;
import ro.tuc.ds2020.services.ChatService;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/send")
    public ResponseEntity<Message> postMessage(@RequestBody Message newMessage) {
        Message savedMessage = chatService.saveMessage(newMessage);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Message>> fetchAllMessages() {
        List<Message> messages = chatService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @MessageMapping("/broadcast")
    @SendTo("/topic/messages")
    public Message broadcastMessage(Message incomingMessage) {
        System.out.println("Broadcasting message: " + incomingMessage);
        chatService.saveMessage(incomingMessage);
        return incomingMessage;
    }
}
