package pgno51.landlink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pgno51.landlink.model.Chat;
import pgno51.landlink.model.Message;
import pgno51.landlink.model.User;
import pgno51.landlink.repo.ChatRepo;
import pgno51.landlink.repo.MessageRepo;
import pgno51.landlink.repo.UserRepo;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatRepo chatRepository;
    private final UserRepo userRepository;
    private final MessageRepo messageRepository;

    public ChatController(ChatRepo chatRepository, UserRepo userRepository, MessageRepo messageRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @PostMapping
    public ResponseEntity<Chat> createChat(@RequestBody CreateChatRequest request) {
        int user1Id = request.getUser1Id();
        int user2Id = request.getUser2Id();

        // Validate users exist
        User user1 = userRepository.findById(user1Id)
                .orElseThrow(() -> new IllegalArgumentException("User1 not found"));
        User user2 = userRepository.findById(user2Id)
                .orElseThrow(() -> new IllegalArgumentException("User2 not found"));

        // Check for existing chat
        Chat existingChat = chatRepository.findChatBetweenUsers(user1Id, user2Id);
        if (existingChat != null) {
            return ResponseEntity.ok(existingChat);
        }

        // Create new chat
        Chat chat = new Chat();
        chat.setUser1(user1);
        chat.setUser2(user2);
        Chat savedChat = chatRepository.save(chat);
        return ResponseEntity.ok(savedChat);
    }

    @GetMapping("/{chatId}/messages")
    public List<Message> getChatMessages(@PathVariable Long chatId) {
        return messageRepository.findByChatIdOrderByTimestampAsc(chatId);
    }

    // DTO for request
    static class CreateChatRequest {
        private int user1Id;
        private int user2Id;

        public int getUser1Id() {
            return user1Id;
        }

        public void setUser1Id(int user1Id) {
            this.user1Id = user1Id;
        }

        public int getUser2Id() {
            return user2Id;
        }

        public void setUser2Id(int user2Id) {
            this.user2Id = user2Id;
        }
    }
}
