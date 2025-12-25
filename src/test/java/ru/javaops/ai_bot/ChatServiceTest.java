package ru.javaops.ai_bot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.javaops.ai_bot.ai.ChatService;

@ActiveProfiles({"dev", "test"})
@SpringBootTest
@Slf4j
class ChatServiceTest {
    @Autowired
    private ChatService chatService;

    @Test
    void generateAnswer() {
        String answer = chatService.generateAnswer("Тест");
        log.info(answer);
    }
}