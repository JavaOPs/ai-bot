package ru.javaops.ai_bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

@Component
@Profile("!test")
@Slf4j
@RequiredArgsConstructor
public class BotRegister implements CommandLineRunner {
    private final AIBot aiBot;

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