package ru.jafti.braintalk.ui.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jafti.braintalk.server.api.ActiveTalkersApi;

import java.util.List;

@Component
public class ChatController {
    @FXML private ListView<String> userList;
    @FXML private TextArea chatHistory;
    @FXML private TextField messageInput;

    @Autowired
    private ActiveTalkersApi activeTalkersApi;

    @FXML
    private void initialize() {
        ActiveTalkersApi.ActiveTalkersResponse activeTalkersResponse = activeTalkersApi.fetchAll();
        List<String> activeNicknames = activeTalkersResponse.activeTalkers()
                .stream()
                .map(ActiveTalkersApi.ActiveTalker::nickname)
                .toList();

        userList.getItems().addAll(activeNicknames);
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