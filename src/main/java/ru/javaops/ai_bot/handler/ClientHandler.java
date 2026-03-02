package ru.javaops.ai_bot.handler;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.client.jetty.JettyTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.javaops.ai_bot.error.TelegramException;

@Slf4j
public class ClientHandler {
    private final TelegramClient telegramClient;

    public ClientHandler(String token) {
        this.telegramClient = new JettyTelegramClient(token);
    }

    public void send(long tgId, String txt) {
        SendMessage sendMsg = new SendMessage(String.valueOf(tgId), txt);
        try {
            telegramClient.execute(sendMsg);
        } catch (TelegramApiException e) {
            log.error(new TelegramException(e).getMessage());
        }
    }
}
