package ru.javaops.ai_bot.handler;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import ru.javaops.ai_bot.error.TelegramException;

import java.util.List;

@UtilityClass
public class UpdateHandler {

    public static Message getMessage(Update upd) {
        return upd.hasMessage() ? upd.getMessage() : upd.getEditedMessage();
    }

    //    from org.telegram.abilitybots.api.util.AbilityUtils (https://github.com/rubenlagus/TelegramBots/tree/master/telegrambots-abilities)
    public static long getChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getChatId();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId();
        } else if (update.hasInlineQuery()) {
            return update.getInlineQuery().getFrom().getId();
        } else if (update.hasChannelPost()) {
            return update.getChannelPost().getChatId();
        } else if (update.hasEditedChannelPost()) {
            return update.getEditedChannelPost().getChatId();
        } else if (update.hasEditedMessage()) {
            return update.getEditedMessage().getChatId();
        } else if (update.hasChosenInlineQuery()) {
            return update.getChosenInlineQuery().getFrom().getId();
        } else if (update.hasShippingQuery()) {
            return update.getShippingQuery().getFrom().getId();
        } else if (update.hasPreCheckoutQuery()) {
            return update.getPreCheckoutQuery().getFrom().getId();
        } else if (update.hasPollAnswer()) {
            return update.getPollAnswer().getUser().getId();
        } else if (update.hasMyChatMember()) {
            return update.getMyChatMember().getChat().getId();
        } else if (update.hasChatMember()) {
            return update.getChatMember().getChat().getId();
        } else if (update.hasChatJoinRequest()) {
            return update.getChatJoinRequest().getChat().getId();
        } else {
            throw new TelegramException("Could not retrieve originating chat ID from update");
        }
    }

    public static User getFrom(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getFrom();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom();
        } else if (update.hasInlineQuery()) {
            return update.getInlineQuery().getFrom();
        } else if (update.hasChannelPost()) {
            return update.getChannelPost().getFrom();
        } else if (update.hasEditedChannelPost()) {
            return update.getEditedChannelPost().getFrom();
        } else if (update.hasEditedMessage()) {
            return update.getEditedMessage().getFrom();
        } else if (update.hasChosenInlineQuery()) {
            return update.getChosenInlineQuery().getFrom();
        } else if (update.hasShippingQuery()) {
            return update.getShippingQuery().getFrom();
        } else if (update.hasPreCheckoutQuery()) {
            return update.getPreCheckoutQuery().getFrom();
        } else if (update.hasPollAnswer()) {
            return update.getPollAnswer().getUser();
        } else if (update.hasMyChatMember()) {
            return update.getMyChatMember().getFrom();
        } else if (update.hasChatMember()) {
            return update.getChatMember().getFrom();
        } else if (update.hasChatJoinRequest()) {
            return update.getChatJoinRequest().getUser();
        } else {
            throw new TelegramException("Could not retrieve originating user from update");
        }
    }

    public static String recoverMarkdown(Message message) {
        String text = message.getText();
        List<MessageEntity> entities = message.getEntities();

        if (text == null || entities == null || entities.isEmpty()) {
            return text; // Return plain text if no entities
        }

        // We need to process entities in reverse order of their offsets
        // to avoid messing up the string indices when inserting markdown symbols
        entities.sort((e1, e2) -> e2.getOffset() - e1.getOffset());
        StringBuilder markdownBuilder = new StringBuilder(text);

        for (MessageEntity entity : entities) {
            int offset = entity.getOffset();
            int length = entity.getLength();

            switch (entity.getType()) {
                case "bold":
                    markdownBuilder.insert(offset + length, "*");
                    markdownBuilder.insert(offset, "*");
                    break;
                case "italic":
                    markdownBuilder.insert(offset + length, "_");
                    markdownBuilder.insert(offset, "_");
                    break;
                case "code":
                    markdownBuilder.insert(offset + length, "`");
                    markdownBuilder.insert(offset, "`");
                    break;
                case "pre":
                    markdownBuilder.insert(offset + length, "```");
                    markdownBuilder.insert(offset, "```");
                    break;
                case "text_link":
                    String url = entity.getUrl();
                    markdownBuilder.insert(offset + length, "](" + url + ")");
                    markdownBuilder.insert(offset, "[");
                    break;
                // Add other entity types as needed
            }
        }
        return markdownBuilder.toString();
    }
}
