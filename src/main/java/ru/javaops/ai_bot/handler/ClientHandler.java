package ru.javaops.ai_bot.handler;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.client.jetty.JettyTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.javaops.ai_bot.error.TelegramException;

import java.util.function.Consumer;

@Slf4j
public class ClientHandler {
    private final TelegramClient telegramClient;

    public ClientHandler(String token) {
        this.telegramClient = new JettyTelegramClient(token);
    }

    public void send(long tgId, String txt) {
        send0(tgId, txt, msg -> msg.enableMarkdown(false));
    }

    public void sendHtml(long tgId, String txt) {
        send0(tgId, txt, msg -> msg.enableHtml(true));
    }

    //    https://developers.sinch.com/docs/conversation/channel-support/telegram/markdown/#markdown-syntax-on-the-telegram-bot-channel
    public void sendMd(long tgId, String txt) {
        send0(tgId, txt, msg -> msg.enableMarkdown(true));
    }

    public void send0(long tgId, String txt, Consumer<SendMessage> consumer) {
        SendMessage sendMsg = new SendMessage(String.valueOf(tgId), txt);
        consumer.accept(sendMsg);
        try {
            telegramClient.execute(sendMsg);
        } catch (TelegramApiException e) {
            log.error(new TelegramException(e).getMessage());
        }
    }
}
