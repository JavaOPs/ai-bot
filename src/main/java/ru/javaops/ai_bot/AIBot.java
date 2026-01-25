package ru.javaops.ai_bot;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import ru.javaops.ai_bot.handler.ClientHandler;
import ru.javaops.ai_bot.handler.CommandHandler;
import ru.javaops.ai_bot.handler.UpdateHandler;

import static ru.javaops.ai_bot.AIBot.Stage.*;

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
    protected final States<Stage> states = new States<>();

    public enum Stage {
        HANDLE_SYNTAX_QUESTION,
        HANDLE_BASE_PROGRAM_QUESTION,
        HANDLE_BASE_TEST_QUESTION,
        HANDLE_TOP_PROGRAM_QUESTION,
        HANDLE_TOP_TEST_QUESTION;
    }

    public AIBot(String botToken) {
        clientHandler = new ClientHandler(botToken);
    }

    @Override
    public void consume(Update update) {
        long tgId = UpdateHandler.getFrom(update).getId();
        Stage state = states.getCurrent(tgId);
        log.info("Update with state {} received from {}", state, tgId);
        Message msg = UpdateHandler.getMessage(update);
        if (CommandHandler.isHelp(msg)) {
            clientHandler.sendMd(tgId, """
                    We wish you success in career with Java! Have a look at
                    💥 [JavaOPs Roadmap](https://javaops.ru/view/roadmap?ref=aibot)
                    💥 [Тест на знание Java, общий и по темам](https://t.me/JavaOPsTestBot)
                    💥 [Материалы для подготовки](https://javaops.ru/view/test?ref=aibot)
                    """);
        } else if (CommandHandler.isStart(msg) || state == null) {
            sendYesNo(tgId, """
                    Take short test for right Java course for you.
                    Do you know Java syntax?
                    """, HANDLE_SYNTAX_QUESTION);
        } else {
            switch (state) {
                case HANDLE_SYNTAX_QUESTION -> UpdateHandler.treatNoAndYes(update,
                        () -> finish(tgId, START_JAVA_COURSE),
                        () -> sendYesNo(tgId, "Do you know Java Core, JDBC and Servlets?\nAre you aware of [BaseJava program](https://javaops.ru/view/basejava#program)?",
                                HANDLE_BASE_PROGRAM_QUESTION)
                );
                case HANDLE_BASE_PROGRAM_QUESTION -> UpdateHandler.treatNoAndYes(update,
                        () -> sendYesNo(tgId, """
                                        Try first Java Web developer course lesson and test task:
                                         [Homework HW1](https://github.com/JavaOPs/basejava/blob/master/lesson/lesson1.md#домашнее-задание-hw1). Can you handle it?""",
                                HANDLE_BASE_TEST_QUESTION),
                        () -> sendYesNo(tgId, "Do you know Maven, Spring, JPA, REST?\nAre you aware of [TopJava program](https://javaops.ru/view/topjava#schedule)?",
                                HANDLE_TOP_PROGRAM_QUESTION)
                );
                case HANDLE_BASE_TEST_QUESTION -> UpdateHandler.treatNoAndYes(update,
                        () -> finish(tgId, START_JAVA_COURSE),
                        () -> finish(tgId, BASE_JAVA_COURSE)
                );
                case HANDLE_TOP_PROGRAM_QUESTION -> UpdateHandler.treatNoAndYes(update,
                        () -> sendYesNo(tgId,
                                """
                                        Try introductory Java Enterprise developer course lesson and
                                        [Homework HW0](https://github.com/JavaOPs/topjava#-домашнее-задание-hw0). Can you handle it?""",
                                HANDLE_TOP_TEST_QUESTION),
                        () -> finish(tgId, CLOUD_JAVA_COURSE));
                case HANDLE_TOP_TEST_QUESTION -> UpdateHandler.treatNoAndYes(update,
                        () -> finish(tgId, BASE_JAVA_COURSE),
                        () -> finish(tgId, TOP_JAVA_COURSE));
            }
        }
    }

    private void sendYesNo(long tgId, String msg, Stage nextStage) {
        clientHandler.sendMdAndKeyboard(tgId, msg, UpdateHandler.YES_NO_KEYBOARD);
        states.update(tgId, nextStage);
    }

    private void finish(long tgId, String course) {
        clientHandler.sendMd(tgId, "Have a look at " + course);
        states.invalidate(tgId);
    }
}
