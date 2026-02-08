package ru.javaops.ai_bot.error;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class TelegramException extends AppException {
    private int errorCode = -1;

    public TelegramException(String msg) {
        super(msg);
    }

    public TelegramException(TelegramApiException ex) {
        super(ex instanceof TelegramApiRequestException tae ? tae.getApiResponse() : ex.toString());
        errorCode = ex instanceof TelegramApiRequestException tae ? tae.getErrorCode() : -1;
    }

    public boolean isForbidden() {
        return errorCode == 403;
    }
}