package ru.javaops.ai_bot.ai;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ChatService {
    private final ChatClient chatClient;

    public String generateAnswer(String question) {
        log.info("generateAnswer");
        // Use the fluent API to construct and send the prompt
        return this.chatClient.prompt()
                .user(question)
                .call()
                .content(); // Get the response as a simple String
    }
}
