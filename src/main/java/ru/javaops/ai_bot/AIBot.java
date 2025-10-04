package ru.javaops.ai_bot;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

//https://rubenlagus.github.io/TelegramBotsDocumentation/getting-started.html
@Slf4j
public class AIBot implements LongPollingSingleThreadUpdateConsumer {
    @Override
    public void consume(Update update) {
        log.info("Update received");
        if (update.hasMessage() && update.getMessage().hasText()) {
            log.info(update.getMessage().getText());
        }
    }
}
