package ru.javaops.ai_bot;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

import java.util.Objects;

@Slf4j
public class AIBotMain {
    public static void main(String[] args) throws Exception {
        String botToken = Objects.requireNonNull(System.getenv("BOT_HTTP_API_TOKEN"), "Set BOT_HTTP_API_TOKEN environment variable"); //"BOT_HTTP_API_Token";
        // https://rubenlagus.github.io/TelegramBotsDocumentation/getting-started.html
        try (TelegramBotsLongPollingApplication botsApp = new TelegramBotsLongPollingApplication()) {
            botsApp.registerBot(botToken, new AIBot(botToken));
            log.info("Bot registered");
            Thread.currentThread().join();  // wait
        }
    }
}
