package ru.javaops.ai_bot;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import ru.javaops.ai_bot.handler.ClientHandler;
import ru.javaops.ai_bot.handler.CommandHandler;
import ru.javaops.ai_bot.handler.UpdateHandler;

import static ru.javaops.ai_bot.handler.UpdateHandler.*;

// https://rubenlagus.github.io/TelegramBotsDocumentation/lesson-6.html
@Slf4j
public class AIBot implements LongPollingSingleThreadUpdateConsumer {

    private static final String START_JAVA_COURSE = "Java for beginners: [StartJava](https://javaops.ru/view/startjava?ref=aibot)";
    private static final String BASE_JAVA_COURSE = "Web Java Developer: [BaseJava](https://javaops.ru/view/basejava?ref=aibot)";
    private static final String TOP_JAVA_COURSE = "Enterprise Java Developer: [TopJava](https://javaops.ru/view/topjava?ref=aibot)";
    private static final String CLOUD_JAVA_COURSE = """
            [Middle to Senior courses](https://javaops.ru/#senior?ref=aibot).
            Main are [Microservices, Kafka, Docker, Spring Cloud, reactive stack](https://javaops.ru/view/cloudjava?ref=aibot)
            and [Deploy microservices to Kubernetes. Helm](https://javaops.ru/view/cloudjava2?ref=aibot)
            """;

    protected final ClientHandler clientHandler;

    public AIBot(String botToken) {
        clientHandler = new ClientHandler(botToken);
    }

    @Override
    public void consume(Update update) {
        long tgId = UpdateHandler.getFrom(update).getId();
        log.info("Update received from {}", tgId);
        Message msg = UpdateHandler.getMessage(update);
        if (CommandHandler.isHelp(msg)) {
            clientHandler.sendMd(tgId, """
                    We wish you success in career with Java! Have a look at
                    💥 [JavaOPs Roadmap](https://javaops.ru/view/roadmap?ref=aibot)
                    💥 [Тест на знание Java, общий и по темам](https://t.me/JavaOPsTestBot)
                    💥 [Материалы для подготовки](https://javaops.ru/view/test?ref=aibot)
                    """);
        } else if (CommandHandler.isStart(msg)) {
            clientHandler.sendMdAndKeyboard(tgId, """
                    Take short test for right Java course for you.
                    Do you know Java syntax?
                    """, YES_NO_KEYBOARD);
        } else if (update.hasCallbackQuery()) {
            String cbData = UpdateHandler.getDataFromCallbackQuery(update);
            switch (cbData) {
                case YES -> clientHandler.sendMd(tgId, "How high out of 10 would you rate your Java knowledge?");
                case NO -> finish(tgId, START_JAVA_COURSE);
            }
        } else if (msg != null) {
            try {
                // primitive in case allowed with JDK 23+
                finish(tgId, switch (Integer.parseInt(msg.getText())) {
                    case int i when i <= 2 -> START_JAVA_COURSE;
                    case int i when i <= 4 -> BASE_JAVA_COURSE;
                    case int i when i <= 6 -> TOP_JAVA_COURSE;
                    default -> CLOUD_JAVA_COURSE;
                });
            } catch (NumberFormatException e) {
                clientHandler.sendMd(tgId, "Rate must be an integer");
            }
        }
    }

    private void finish(long tgId, String course) {
        clientHandler.sendMd(tgId, "Have a look at " + course);
    }
}
