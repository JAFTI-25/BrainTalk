package ru.jafti.braintalk.ui;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.jafti.braintalk.ui.javafx.JavaFxConfig;

@SpringBootApplication
public class BrainTalkUiClient {
    public static void main(String[] args) {
        Application.launch(JavaFxConfig.class, args);
    }
}
