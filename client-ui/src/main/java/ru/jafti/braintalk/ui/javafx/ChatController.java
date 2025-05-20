package ru.jafti.braintalk.ui.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class ChatController {
    @FXML private ListView<String> userList;
    @FXML private TextArea chatHistory;
    @FXML private TextField messageInput;

    @FXML
    private void initialize() {
        // Заглушка: тестовые данные
        userList.getItems().addAll("User1", "User2", "User3");
        chatHistory.setText("История переписки...");

        // Обработчик выбора пользователя
        userList.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> updateChatHistory(newVal)
        );
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
}