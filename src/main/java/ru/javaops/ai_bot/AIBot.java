package ru.javaops.ai_bot;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import ru.javaops.ai_bot.handler.ClientHandler;
import ru.javaops.ai_bot.handler.UpdateHandler;

// https://rubenlagus.github.io/TelegramBotsDocumentation/lesson-1.html
@Slf4j
public class AIBot implements LongPollingSingleThreadUpdateConsumer {
    protected final ClientHandler clientHandler;

    public AIBot(String botToken) {
        clientHandler = new ClientHandler(botToken);
    }

    @Override
    public void consume(Update update) {
        log.info("Update received");
        User from = UpdateHandler.getFrom(update);
        Message message = UpdateHandler.getMessage(update);
        if (message != null) {
            String mdMsg = UpdateHandler.recoverMarkdown(message);
            // tg_id==chat_id for bot-user chat (https://stackoverflow.com/a/42786449/548473)
            clientHandler.sendMd(from.getId(), mdMsg);
        }
    }
}
