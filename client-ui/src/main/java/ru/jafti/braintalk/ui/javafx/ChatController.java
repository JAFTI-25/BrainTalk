package ru.jafti.braintalk.ui.javafx;

import jakarta.annotation.PreDestroy;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jafti.braintalk.server.api.ActiveTalkersApi;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    @FXML private ListView<String> userList;
    @FXML private TextArea chatHistory;
    @FXML private TextField messageInput;

    @Autowired
    private ActiveTalkersApi activeTalkersApi;

    // ObservableList для автоматического обновления UI
    private final ObservableList<String> onlineUsers = FXCollections.observableArrayList();
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    @FXML
    private void initialize() {
        // Связываем список с UI
        userList.setItems(onlineUsers);

        // Первоначальная загрузка
        refreshUserList();

        // Периодическое обновление каждые 5 секунд
        executor.scheduleAtFixedRate(this::refreshUserList, 0, 5, TimeUnit.SECONDS);

        // Обработчик выбора пользователя
        userList.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> updateChatHistory(newVal)
        );
    }

    private void refreshUserList() {
        Platform.runLater(() -> {
            try {
                log.info("Refresh active users");
                ActiveTalkersApi.ActiveTalkersResponse response = activeTalkersApi.fetchAll();
                Set<String> currentUsers = response.activeTalkers()
                        .stream()
                        .map(ActiveTalkersApi.ActiveTalker::nickname)
                        .collect(Collectors.toSet());

                // Обновляем список только если есть изменения
                if (!onlineUsers.containsAll(currentUsers)) {
                    onlineUsers.setAll(currentUsers);
                }
            } catch (Exception e) {
                log.error("Ошибка обновления списка: " + e.getMessage());
            }
        });
    }

    private void updateChatHistory(String username) {
        chatHistory.setText("Переписка с " + username + ":\n---\n");
    }

    @FXML
    private void sendMessage() {
        String message = messageInput.getText();
        if (!message.isEmpty()) {
            chatHistory.appendText("Вы: " + message + "\n");
            messageInput.clear();
        }
    }

    @PreDestroy
    public void cleanup() {
        executor.shutdown(); // Важно закрыть при завершении
    }
}