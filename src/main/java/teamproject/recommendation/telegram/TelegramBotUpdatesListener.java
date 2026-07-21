package teamproject.recommendation.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import teamproject.recommendation.repository.UserRepository;
import teamproject.recommendation.service.RecommendationService;

import java.util.List;

@Component
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final TelegramBot telegramBot;
    private final UserRepository userRepository;
    private final RecommendationService recommendationService;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, UserRepository userRepository, RecommendationService recommendationService) {
        this.telegramBot = telegramBot;
        this.userRepository = userRepository;
        this.recommendationService = recommendationService;
        this.telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {

        for (Update update : updates) {

            Message message = update.message();

            if (message == null || message.text() == null) {
                continue;
            }

            if ("/start".equals(message.text())) {

                telegramBot.execute(
                        new SendMessage(
                                message.chat().id(),
                                """
                                Здравствуйте!

                                Доступная команда:

                                /recommend username
                                """
                        )
                );

                continue;
            }

            if (message.text().startsWith("/recommend ")) {

                String username = message.text().substring("/recommend ".length()).trim();

                var users = userRepository.findByUsername(username);

                if (users.size() != 1) {

                    telegramBot.execute(
                            new SendMessage(
                                    message.chat().id(),
                                    "Пользователь не найден"
                            )
                    );

                    continue;
                }

                UserRepository.User user = users.get(0);

                var recommendationResponse =
                        recommendationService.getRecommendation(user.id());

                StringBuilder response = new StringBuilder();

                response.append("Здравствуйте ")
                        .append(user.firstName())
                        .append(" ")
                        .append(user.lastName())
                        .append("\n\n")
                        .append("Новые продукты для вас:\n");

                for (var recommendation : recommendationResponse.getRecommendations()) {

                    response.append("• ")
                            .append(recommendation.getName())
                            .append("\n")
                            .append(recommendation.getText())
                            .append("\n\n");
                }

                telegramBot.execute(
                        new SendMessage(
                                message.chat().id(),
                                response.toString()
                        )
                );

                continue;
            }
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}