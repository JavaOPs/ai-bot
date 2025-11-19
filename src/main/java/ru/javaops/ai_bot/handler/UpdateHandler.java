package ru.javaops.ai_bot.handler;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import ru.javaops.ai_bot.error.TelegramException;

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
}
