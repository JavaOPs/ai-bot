package ru.javaops.ai_bot.handler;

import org.telegram.telegrambots.meta.api.objects.message.Message;

public class CommandHandler {
    public static final String START_COMMAND = "/start";
    public static final String HELP_COMMAND = "/help";

    public static boolean isHelp(Message msg) {
        return isCommand(msg, HELP_COMMAND);
    }

    public static boolean isStart(Message msg) {
        return isCommand(msg, START_COMMAND);
    }

    private static boolean isCommand(Message msg, String command) {
        return msg != null && msg.hasText() && msg.getText().startsWith(command);
    }
}
