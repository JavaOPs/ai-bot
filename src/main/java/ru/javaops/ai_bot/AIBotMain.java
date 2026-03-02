package ru.javaops.ai_bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class AIBotMain implements CommandLineRunner {
    private final AIBot aiBot;

    static void main(String[] args) {
        SpringApplication.run(AIBotMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // https://rubenlagus.github.io/TelegramBotsDocumentation/getting-started.html
        try (TelegramBotsLongPollingApplication botsApp = new TelegramBotsLongPollingApplication()) {
            botsApp.registerBot(aiBot.getToken(), aiBot);
            log.info("Bot registered");
            Thread.currentThread().join();  // wait
        }
    }
}
