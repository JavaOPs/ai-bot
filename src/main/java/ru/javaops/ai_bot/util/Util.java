package ru.javaops.ai_bot.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import ru.javaops.ai_bot.error.TelegramException;

@UtilityClass
public class Util {
    @NonNull
    public static <T> T notNull(T value, String msg) {
        if (value == null) throw new TelegramException(msg);
        return value;
    }
}