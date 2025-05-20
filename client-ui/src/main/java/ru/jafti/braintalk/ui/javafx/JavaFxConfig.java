package ru.jafti.braintalk.ui.javafx;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import ru.jafti.braintalk.ui.BrainTalkUiClient;

import java.io.IOException;

public class JavaFxConfig extends Application {
    private ConfigurableApplicationContext applicationContext;
    private Scene scene;

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(BrainTalkUiClient.class).run();
    }

    @Override
    public void start(Stage stage) {
        scene = new Scene(loadFXML(), 640, 480);
        stage.setScene(scene);
        stage.show();
        applicationContext.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    private static Parent loadFXML() {
        FXMLLoader loader = new FXMLLoader(BrainTalkUiClient.class.getResource("/fxml/chat-view.fxml"));
        try {
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage stage) {
            super(stage);
        }
        public Stage getStage() {
            return (Stage) getSource();
        }
    }
}
